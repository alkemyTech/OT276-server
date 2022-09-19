package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.NewResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NewControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @WithUserDetails("admin@somosmas.org")
    void createEntity_shouldReturn201() throws Exception {
        CreateNewRequest request = CreateNewRequest.builder()
                .name("foo")
                .content("bar")
                .image("")
                .categoryId(1L)
                .build();

        final String actualLocation = mockMvc.perform(post(ApiConstants.NEWS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/new/1");
    }


    @Test
    @Order(2)
    @WithUserDetails("admin@somosmas.org")
    void getNew_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.NEWS_URI + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        NewResponse response = JsonUtils.jsonToObject(content, NewResponse.class);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getName()).isEqualTo("nombre");
    }

    @Test
    @Order(3)
    @WithAnonymousUser
    void getNew_shouldReturn401() throws Exception {
        mockMvc.perform(get(ApiConstants.NEWS_URI + "/1"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @Order(4)
    @WithUserDetails("jdoe@somosmas.org")
    void getNew_shouldReturn404() throws Exception {
        mockMvc.perform(get(ApiConstants.NEWS_URI + "/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Order(5)
    @WithUserDetails("admin@somosmas.org")
    void deleteNew_shouldReturn204() throws Exception {
        mockMvc.perform(delete(ApiConstants.NEWS_URI + "/1"))
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    @Test
    @Order(6)
    @WithUserDetails("jdoe@somosmas.org")
    void deleteNew_shouldReturn403() throws Exception {
        mockMvc.perform(delete(ApiConstants.NEWS_URI + "/1"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}