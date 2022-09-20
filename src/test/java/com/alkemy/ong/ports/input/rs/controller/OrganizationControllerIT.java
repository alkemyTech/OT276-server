package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganizationControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @WithUserDetails("admin@somosmas.org")
    void getOrganization_shouldReturn200() throws Exception{

        String content = mockMvc.perform(get(ApiConstants.ORGANIZATIONS_URI + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        OrganizationResponse response = JsonUtils.jsonToObject(content, OrganizationResponse.class);

        assertThat(response.getName()).isEqualTo("Somos Mas");
        assertThat(response.getImage()).isEqualTo("https://cohorte-agosto-38d749a7.s3.amazonaws.com/1661519321851-LOGO-SOMOS_MAS.png");
        assertThat(response.getPhone()).isEqualTo(1160112988);
        assertThat(response.getAddress()).isEqualTo("La Cava");
        assertThat(response.getFacebookUrl()).isEqualTo("Somos_Mas");
        assertThat(response.getLinkedinUrl()).isEqualTo("Somos Mas");
        assertThat(response.getInstagramUrl()).isEqualTo("SomosMas");
    }

    @Test
    @Order(2)
    @WithUserDetails("admin@somosmas.org")
    void updateOrganization_shouldReturn204() throws Exception{

        UpdateOrganizationRequest request = UpdateOrganizationRequest.builder()
                .image("image")
                .address("address")
                .phone(123)
                .email("email@gmail.com")
                .welcomeText("welcome")
                .aboutUsText("about us")
                .facebookUrl("facebook")
                .instagramUrl("instagram")
                .linkedinUrl("linkedin")
                .build();

        mockMvc.perform(patch(ApiConstants.ORGANIZATIONS_URI+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @Order(3)
    @WithUserDetails("admin@somosmas.org")
    void getSlidesByOrganizationId_shouldReturn200() throws Exception{

        String content = mockMvc.perform(get(ApiConstants.ORGANIZATIONS_URI + "/1/slides"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        List<SlideResponse> response = JsonUtils.jsonToCollection(content, List.class, SlideResponse.class);

        assertThat(response.size()).isEqualTo(1);
    }

    @Test
    @Order(4)
    @WithUserDetails("jsmmith@somosmas.org")
    void updateOrganization_shouldReturn403() throws Exception{
        UpdateOrganizationRequest request = UpdateOrganizationRequest.builder()
                .image("image")
                .address("address")
                .phone(123)
                .email("email@gmail.com")
                .welcomeText("welcome")
                .aboutUsText("about us")
                .facebookUrl("facebook")
                .instagramUrl("instagram")
                .linkedinUrl("linkedin")
                .build();

        mockMvc.perform(patch(ApiConstants.ORGANIZATIONS_URI+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(5)
    @WithAnonymousUser
    void getSlidesByOrganizationId_shouldReturn401() throws Exception {

        mockMvc.perform(get(ApiConstants.ORGANIZATIONS_URI + "/1/slides"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
