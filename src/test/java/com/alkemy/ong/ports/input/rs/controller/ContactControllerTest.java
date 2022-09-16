package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.config.util.JsonUtils;
import com.alkemy.ong.core.model.Contact;
import com.alkemy.ong.core.usecase.ContactService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.ContactControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import com.alkemy.ong.ports.input.rs.response.ContactResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @InjectMocks
    ContactController controller;
    @Mock
    ContactService service;
    @Spy
    ContactControllerMapper mapper = Mappers.getMapper(ContactControllerMapper.class);
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void createContacts() throws Exception {

        CreateContactRequest request = CreateContactRequest.builder()
                .name("nombre")
                .phone(42464282)
                .message("Mensaje")
                .email("usuario@gmail.com")
                .build();

        given(service.createEntity(any(Contact.class))).willReturn(99L);

        final String actualLocation = mockMvc.perform(post(ApiConstants.CONTACTS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/contacts/99");
    }

    @Test
    void getContacts() throws Exception {

        Contact contact = new Contact();
        Contact contact2 = new Contact();

        contact.setId(99L);
        contact.setName("nombre");
        contact.setEmail("usuario@gmail.com");
        contact.setPhone(42464282);
        contact.setMessage("Mensaje");

        contact2.setId(99L);
        contact2.setName("nombre");
        contact2.setEmail("usuario@gmail.com");
        contact2.setPhone(42464282);
        contact2.setMessage("Mensaje");


        List<Contact> contacts = new ArrayList<>();

        contacts.add(contact);
        contacts.add(contact2);

        given(service.getContacts()).willReturn(contacts);

        String content = mockMvc.perform(get(ApiConstants.CONTACTS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        List<ContactResponse> response = JsonUtils.jsonToCollection(content, List.class, ContactResponse.class);

        assertThat(response.size()).isEqualTo(2);

        response.forEach(
                contactResponse -> {
                    assertThat(contactResponse.getName()).isEqualTo("nombre");
                    assertThat(contactResponse.getEmail()).isEqualTo("usuario@gmail.com");
                    assertThat(contactResponse.getPhone()).isEqualTo(42464282);
                    assertThat(contactResponse.getMessage()).isEqualTo("Mensaje");
                }
        );
    }
}