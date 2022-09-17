package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponseList;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestimonialControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @WithUserDetails("admin@somosmas.org")
    void createTestimonial_shouldReturn201() throws Exception {

        TestimonialRequest request = TestimonialRequest.builder()
                .name("testi")
                .content("hola")
                .build();
        final String actualLocation = mockMvc.perform(post(ApiConstants.TESTIMONIALS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/testimonials/1");

    }

    @Test
    @Order(2)
    @WithUserDetails("admin@somosmas.org")
    void getTestimonial_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.TESTIMONIALS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        TestimonialResponseList response = JsonUtils.jsonToObject(content, TestimonialResponseList.class);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/testimonials?page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/testimonials?page=0");
        assertThat(response.getContent()).isNotNull();

    }

    @Test
    @Order(3)
    @WithUserDetails("admin@somosmas.org")
    void updateTestimonial_shouldReturn200() throws Exception {

        TestimonialRequest request = TestimonialRequest.builder()
                .name("Updated Name")
                .content("New Content")
                .image("Nueva Imagen")
                .build();

        String content = mockMvc.perform(put(ApiConstants.TESTIMONIALS_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        TestimonialResponse response = JsonUtils.jsonToObject(content, TestimonialResponse.class);

        assertThat(response.getName()).isEqualTo("Updated Name");
        // todo agregar otros asserts

    }
    @Test
    @Order(4)
    @WithUserDetails("admin@somosmas.org")
    void deleteTestimonial_shouldReturn204() throws Exception {
        mockMvc.perform(delete(ApiConstants.TESTIMONIALS_URI + "/{id}", 1L))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


}
