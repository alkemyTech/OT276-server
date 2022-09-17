package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.request.LoginRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void register_shouldReturn201() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@test.com")
                .password("test123")
                .firstName("test")
                .lastName("test")
                .photo("img")
                .build();

        final String content = mockMvc.perform(post(ApiConstants.AUTHENTICATION_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).contains("token", "expiration_date");

    }

    @Test
    @Order(2)
    void login_shouldReturn200() throws Exception {
        LoginRequest request = LoginRequest.builder().userName("test@test.com").password("test123").build();
        final String content = mockMvc.perform(post(ApiConstants.AUTHENTICATION_URI + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).contains("token", "expiration_date");
    }

    @Test
    @Order(3)
    @WithUserDetails("test@test.com")
    void getUserInformation_shouldReturn200() throws Exception {
        final String content = mockMvc.perform(get(ApiConstants.AUTHENTICATION_URI + "/me"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        UserResponse response = JsonUtils.jsonToObject(content, UserResponse.class);

        assertThat(response.getFirstName()).isEqualTo("test");


    }

    @Test
    @Order(4)
    void login_shouldReturn400() throws Exception {
        LoginRequest request = LoginRequest.builder().userName("test@test.com").password("123test").build();
        mockMvc.perform(post(ApiConstants.AUTHENTICATION_URI + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(5)
    void register_shouldReturn409() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@test.com")
                .password("test123")
                .firstName("test")
                .lastName("test")
                .photo("img")
                .build();

        mockMvc.perform(post(ApiConstants.AUTHENTICATION_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isConflict());

    }

    @Test
    @Order(6)
    void register_shouldReturn400() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@test.com")
                .password("")
                .firstName("test")
                .lastName("test")
                .photo("img")
                .build();

        mockMvc.perform(post(ApiConstants.AUTHENTICATION_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(7)
    @WithAnonymousUser
    void getUserInformation_shouldReturn401() throws Exception {
        mockMvc.perform(get(ApiConstants.AUTHENTICATION_URI + "/me"))
                .andExpect(status().isUnauthorized());

    }
}