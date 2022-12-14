package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.core.model.Alkymer;
import com.alkemy.ong.core.model.AlkymerList;
import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.model.NewList;
import com.alkemy.ong.core.model.Skill;
import com.alkemy.ong.core.usecase.NewService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.NewControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponseList;
import com.alkemy.ong.ports.input.rs.response.NewResponse;
import com.alkemy.ong.ports.input.rs.response.NewResponseList;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NewControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    NewController controller;

    @Mock
    NewService service;

    @Spy
    NewControllerMapper mapper = Mappers.getMapper(NewControllerMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void createEntity_shouldReturn201() throws Exception {
        CreateNewRequest request = CreateNewRequest.builder()
                .name("foo")
                .content("bar")
                .image("foo")
                .categoryId(1L)
                .build();

        given(service.createEntity(any(New.class),anyLong())).willReturn(22L);

        final String actualLocation = mockMvc.perform(post(ApiConstants.NEWS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/news/22");
    }

    @Test
    void getNew_shouldReturn200() throws Exception {
        Category category = new Category();
        category.setName("politica");

        New news = new New();
        news.setId(22L);
        news.setName("foo");
        news.setCategory(category);

        given(service.getByIdIfExists(22L)).willReturn(news);

        String content = mockMvc.perform(get(ApiConstants.NEWS_URI + "/22"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        NewResponse response = JsonUtils.jsonToObject(content, NewResponse.class);

        assertThat(response.getId()).isEqualTo(22);
        assertThat(response.getName()).isEqualTo("foo");
        assertThat(response.getCategory()).isNotEmpty();
        assertThat(response.getCategory()).isEqualTo("politica");
    }

    @Test
    void getNews_shouldReturn200() throws Exception {

        Category category = new Category();
        category.setName("politica");

        New news = new New();
        news.setId(22L);
        news.setName("foo");
        news.setCategory(category);

        NewList list = new NewList(List.of(news), Pageable.ofSize(1), 1);

        given(service.getList(any(PageRequest.class))).willReturn(list);

        String content = mockMvc.perform(get(ApiConstants.NEWS_URI + "?page=0&size=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        NewResponseList response = JsonUtils.jsonToObject(content, NewResponseList.class);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/news?size=1&page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/news?size=1&page=0");
        assertThat(response.getContent()).isNotEmpty();
    }

    @Test
    void deleteNew_shouldReturn204() throws Exception {
        mockMvc.perform(delete(ApiConstants.NEWS_URI + "/22"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}