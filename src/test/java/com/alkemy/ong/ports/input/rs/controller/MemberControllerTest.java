package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.core.model.MemberList;
import com.alkemy.ong.core.usecase.MemberService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.MemberControllerMapper;
import com.alkemy.ong.ports.input.rs.request.MemberRequest;
import com.alkemy.ong.ports.input.rs.response.MemberResponseList;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class MemberControllerTest {


    private MockMvc mockMvc;

    @InjectMocks
    MemberController memberController;

    @Mock
    MemberService memberService;

    @Spy
    MemberControllerMapper mapper = Mappers.getMapper(MemberControllerMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void createMember_shouldReturn201() throws Exception {

        MemberRequest request = MemberRequest.builder()
                .name("foo")
                .facebookUrl("foo.faceboock")
                .instagramUrl("foo.instagram")
                .linkedinUrl("foo.linkedin")
                .description("foo.description")
                .image("foo.image")
                .build();

        given(memberService.createEntity(any(Member.class))).willReturn(99L);

        final String actualLocation = mockMvc.perform(post(ApiConstants.MEMBERS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/members/99");

    }

    @Test
    void getMembers_shouldReturn200() throws Exception {
        Member member = new Member();
        member.setId(99L);
        member.setName("foo");
        member.setImage("foo.image");

        MemberList list = new MemberList(List.of(member), Pageable.ofSize(1), 1);

        given(memberService.getList(any(PageRequest.class))).willReturn(list);

        String content = mockMvc.perform(get(ApiConstants.MEMBERS_URI + "?page=0&size=1"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertThat(content).isNotBlank();

        MemberResponseList response = JsonUtils.jsonToObject(content, MemberResponseList.class);

            assertThat(response.getTotalElements()).isEqualTo(1);
            assertThat(response.getTotalPages()).isEqualTo(1);
            assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/members?size=1&page=1");
            assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/members?size=1&page=0");
            assertThat(response.getContent()).isNotEmpty();
        }



    @Test
    void upDateMember_shouldReturn204() throws Exception{

        MemberRequest request = MemberRequest.builder()
                .name("faa")
                .image("faa.image")
                .build();

        willDoNothing().given(memberService).updateEntityIfExists(eq(99L), any(Member.class));

        mockMvc.perform(put(ApiConstants.MEMBERS_URI + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteMember_shouldReturn204() throws Exception{
        mockMvc.perform(delete(ApiConstants.MEMBERS_URI + "/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


}