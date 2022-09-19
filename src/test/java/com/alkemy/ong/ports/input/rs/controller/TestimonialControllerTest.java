package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.core.model.TestimonialList;
import com.alkemy.ong.core.usecase.AlkymerService;
import com.alkemy.ong.core.usecase.TestimonialService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.AlkymerControllerMapper;
import com.alkemy.ong.ports.input.rs.mapper.TestimonialControllerMapper;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponseList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class TestimonialControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    TestimonialController controller;

    @Mock
    TestimonialService service;

    @Spy
    TestimonialControllerMapper mapper = Mappers.getMapper(TestimonialControllerMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void createTestimonial_shouldReturn203() throws Exception{

        TestimonialRequest request = TestimonialRequest.builder()
                .name("Testi")
                .content("Content 1")
                .image("image 1")
                .build();
        given(service.createNewTestimonial(any(Testimonial.class))).willReturn(75L);

        final String actualLocation = mockMvc.perform(post(ApiConstants.TESTIMONIALS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/testimonials/75");

    }

    @Test
    void getTestimonial_shouldReturn200() throws Exception{

        Testimonial testimonial = new Testimonial();
        testimonial.setId(75L);
        testimonial.setName("Testi");
        testimonial.setContent("Content 1");

        TestimonialList list = new TestimonialList(List.of(testimonial), Pageable.ofSize(1),1);

        given(service.getList(any(PageRequest.class))).willReturn(list);

        String content = mockMvc.perform(get(ApiConstants.TESTIMONIALS_URI
                        + "?page=0&size=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).isNotBlank();

        TestimonialResponseList response = JsonUtils.jsonToObject(content, TestimonialResponseList.class);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/testimonials?size=1&page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/testimonials?size=1&page=0");
        assertThat(response.getContent()).isNotEmpty();

    }

    @Test
    void deleteTestimonial() {
    }

    @Test
    void getTestimonialList() {
    }

    @Test
    void updateTestimonial() {
    }
}