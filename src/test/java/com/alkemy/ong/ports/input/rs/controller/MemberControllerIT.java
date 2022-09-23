package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.core.model.Member;
import com.alkemy.ong.core.usecase.MemberService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.MemberRequest;
import com.alkemy.ong.ports.input.rs.response.MemberResponseList;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Mock
    MemberService memberService;


    @Test
    @Order(1)
    @WithUserDetails("jdoe@somosmas.org")
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

        assertThat(actualLocation).isEqualTo("http://localhost/v1/members/1");
    }
    @Test
    @Order(2)
    @WithUserDetails("jdoe@somosmas.org")
    void getMembers_shouldReturn200() throws Exception{
        String content = mockMvc.perform(get(ApiConstants.MEMBERS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        MemberResponseList response = JsonUtils.jsonToObject(content, MemberResponseList.class);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/members?page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/members?page=0");
        assertThat(response.getContent()).isNotEmpty();
    }

    @Test
    @Order(3)
    @WithUserDetails("jdoe@somosmas.org")
    void upDateMember_shouldReturn204() throws Exception {
        MemberRequest request = MemberRequest.builder()
                .name("faa")
                .facebookUrl("faa.faceboock")
                .instagramUrl("faa.instagram")
                .linkedinUrl("faa.linkedin")
                .description("faa.description")
                .image("faa.image")
                .build();

        mockMvc.perform(put(ApiConstants.MEMBERS_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @Order(4)
    @WithUserDetails("admin@somosmas.org")
    void deleteMember_shouldReturn204() throws Exception  {
        mockMvc.perform(delete(ApiConstants.MEMBERS_URI + "/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
    @Order(5)
    @WithUserDetails("jdoe@somosmas.org")
    void deleteMember_shouldReturn403() throws Exception {
        mockMvc.perform(delete(ApiConstants.MEMBERS_URI + "/1"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }



}