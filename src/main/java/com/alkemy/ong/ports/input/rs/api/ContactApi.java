package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import com.alkemy.ong.ports.input.rs.response.ContactResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ContactApi {

    ResponseEntity<Void> createContacts(@Valid CreateContactRequest contactRequest);
    
    ResponseEntity<List<ContactResponse>> getContacts();
}
