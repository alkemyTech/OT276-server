package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.core.model.Organization;
import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.usecase.OrganizationService;
import com.alkemy.ong.core.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.OrganizationControllerMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alkemy.ong.ports.input.rs.mapper.SlideControllerMapper;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrganizationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks OrganizationController controller;

    @Mock OrganizationService organizationService;

    @Mock SlideService slideService;

    @Spy OrganizationControllerMapper organizationControllerMapper = Mappers.getMapper(OrganizationControllerMapper.class);

    @Spy
    SlideControllerMapper slideControllerMapper = Mappers.getMapper(SlideControllerMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void getOrganization_shouldReturn200() throws Exception{

        Organization organization = getOrganization();

        given(organizationService.getOrganizationEntity(1L)).willReturn(organization);

        String content = mockMvc.perform(get(ApiConstants.ORGANIZATIONS_URI + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        OrganizationResponse response = JsonUtils.jsonToObject(content, OrganizationResponse.class);

        assertThat(response.getName()).isEqualTo("Somos Mas");
        assertThat(response.getImage()).isEqualTo("urlImagen");
        assertThat(response.getPhone()).isEqualTo(456123);
        assertThat(response.getAddress()).isEqualTo("address");
        assertThat(response.getFacebookUrl()).isEqualTo("facebook");
        assertThat(response.getLinkedinUrl()).isEqualTo("linkedin");
        assertThat(response.getInstagramUrl()).isEqualTo("instagram");
    }


    @Test
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
    void getSlidesByOrganizationId_shouldReturn200() throws Exception{

        Organization organization = getOrganization();
        Slide slide = new Slide();
        slide.setId(1L);
        slide.setImageUrl("urlImage");
        slide.setText("test");
        slide.setOrder(1);
        slide.setOrganization(organization);
        List<Slide> list = new ArrayList<>();
        list.add(slide);

        given(slideService.getListByOrganizationIdAndOrderByOrder(1L)).willReturn(list);

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

    private Organization getOrganization(){
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setAddress("address");
        organization.setImage("urlImagen");
        organization.setName("Somos Mas");
        organization.setPhone(456123);
        organization.setEmail("somosmas@gmail.com");
        organization.setAboutUsText("about us text");
        organization.setContactText("contact text");
        organization.setWelcomeText("welcome text");
        organization.setFacebookUrl("facebook");
        organization.setInstagramUrl("instagram");
        organization.setLinkedinUrl("linkedin");
        return organization;
    }
}
