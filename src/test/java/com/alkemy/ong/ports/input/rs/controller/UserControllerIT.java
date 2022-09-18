package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.UpdateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @WithUserDetails("admin@somosmas.org")
    void getUsers_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.USERS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        List<UserResponse> response = JsonUtils.jsonToCollection(content, List.class, UserResponse.class);

        UserResponse userResponse = new UserResponse(1L, "Admin", "Admin", "admin@somosmas.org", "https://static-url.com/avatar.jpg");
        userResponse.setFirstName("Admin");

        assertThat(response.isEmpty()).isFalse();
        assertThat(response.size()).isEqualTo(3);
        assertThat(response.get(0).equals(userResponse));

    }

    @Test
    @Order(2)
    @WithAnonymousUser
    void getUsers_shouldReturn401() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.USERS_URI))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    @Order(3)
    @WithUserDetails("jdoe@somosmas.org")
    void getUsers_shouldReturn403() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.USERS_URI))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    @Order(4)
    @WithUserDetails("admin@somosmas.org")
    void updateUser_shouldReturn204() throws Exception {

        UpdateUserRequest request = new UpdateUserRequest("NewFirstName", "NewLastName", "12345678", "NewPhoto");
        UserResponse expectedResponse = new UserResponse(2L, "NewFirstName", "NewLastName", "jdoe@somosmas.org", "NewPhoto");

        mockMvc.perform(patch(ApiConstants.USERS_URI + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNoContent())
                .andDo(print());

        String content = mockMvc.perform(get(ApiConstants.USERS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        List<UserResponse> response = JsonUtils.jsonToCollection(content, List.class, UserResponse.class);
        assertThat(response.get(1).equals(expectedResponse)).isTrue();

    }

    @Test
    @Order(5)
    @WithUserDetails("jdoe@somosmas.org")
    void updateUser_shouldReturn400() throws Exception {

        UpdateUserRequest request = new UpdateUserRequest("NewFirstName", "NewLastName", "123", "NewPhoto");

        mockMvc.perform(patch(ApiConstants.USERS_URI + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    @Order(6)
    @WithAnonymousUser
    void updateUser_shouldReturn401() throws Exception {

        UpdateUserRequest request = UpdateUserRequest.builder()
                .build();

        mockMvc.perform(patch(ApiConstants.USERS_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @Order(7)
    @WithUserDetails("jdoe@somosmas.org")
    void updateUser_shouldReturn403() throws Exception {

        UpdateUserRequest request = UpdateUserRequest.builder()
                .build();

        mockMvc.perform(patch(ApiConstants.USERS_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    @Order(8)
    @WithUserDetails("admin@somosmas.org")
    void updateUser_shouldReturn404() throws Exception {

        UpdateUserRequest request = UpdateUserRequest.builder()
                .build();

        mockMvc.perform(patch(ApiConstants.USERS_URI + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @Order(9)
    @WithUserDetails("jdoe@somosmas.org")
    void deleteUser_shouldReturn204() throws Exception {

        mockMvc.perform(delete(ApiConstants.USERS_URI + "/2"))
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    @Test
    @Order(10)
    @WithAnonymousUser
    void deleteUser_shouldReturn401() throws Exception {

        mockMvc.perform(delete(ApiConstants.USERS_URI + "/2"))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @Order(11)
    @WithUserDetails("jsmmith@somosmas.org")
    void deleteUser_shouldReturn403() throws Exception {

        mockMvc.perform(delete(ApiConstants.USERS_URI + "/1"))
                .andExpect(status().isForbidden())
                .andDo(print());

    }

}
