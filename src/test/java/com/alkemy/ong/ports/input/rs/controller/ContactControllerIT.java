package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import com.alkemy.ong.ports.input.rs.response.ContactResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Order(1)
    @WithAnonymousUser
    @ParameterizedTest
    @CsvSource({
            "nombre,42464282,Mensaje,usuario@gmail.com,1",
            "nombre,42464282,Mensaje,usuario@gmail.com,2",
            "nombre,42464282,Mensaje,usuario@gmail.com,3",
    })
    void createContacts_shouldReturn201(String name, Integer phone, String message, String email, Long id) throws Exception {

        CreateContactRequest request = CreateContactRequest.builder()
                .name(name)
                .phone(phone)
                .message(message)
                .email(email)
                .build();

        final String actualLocation = mockMvc.perform(post(ApiConstants.CONTACTS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/contacts/" + id);
    }

    @Test
    @Order(2)
    @WithUserDetails("admin@somosmas.org")
    void getContacts_shouldReturn200() throws Exception {
        String content = mockMvc.perform(get(ApiConstants.CONTACTS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        List<ContactResponse> response = JsonUtils.jsonToCollection(content, List.class, ContactResponse.class);

        assertThat(response.size()).isEqualTo(3);

        response.forEach(
                contactResponse -> {
                    assertThat(contactResponse.getName()).isEqualTo("nombre");
                    assertThat(contactResponse.getEmail()).isEqualTo("usuario@gmail.com");
                    assertThat(contactResponse.getPhone()).isEqualTo(42464282);
                    assertThat(contactResponse.getMessage()).isEqualTo("Mensaje");
                }
        );
    }

    @Test
    @Order(3)
    @WithUserDetails("jdoe@somosmas.org")
    void getContacts_shouldReturn403() throws Exception {
        mockMvc.perform(get(ApiConstants.CONTACTS_URI))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(4)
    @WithAnonymousUser
    void getContacts_shouldReturn401() throws Exception {
        mockMvc.perform(get(ApiConstants.CONTACTS_URI))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}