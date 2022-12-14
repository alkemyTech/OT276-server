package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Contact;
import com.alkemy.ong.core.usecase.ContactService;
import com.alkemy.ong.ports.input.rs.api.ContactApi;
import com.alkemy.ong.ports.input.rs.mapper.ContactControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import com.alkemy.ong.ports.input.rs.response.ContactResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.CONTACTS_URI;

@RestController
@RequestMapping(CONTACTS_URI)
@RequiredArgsConstructor
public class ContactController implements ContactApi {

    private final ContactService service;

    private final ContactControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createContacts(@Valid @RequestBody CreateContactRequest createContactRequest) {

        Contact contact = mapper.createContactRequestToContact(createContactRequest);

        final long id = service.createEntity(contact);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ContactResponse>> getContacts() {

        List<Contact> list = service.getContacts();

        List<ContactResponse> response = mapper.contactListToContactResponseList(list);

        return ResponseEntity.ok().body(response);
    }
}