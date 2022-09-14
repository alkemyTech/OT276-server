package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@SecurityRequirement(name = "bearerAuth")
public interface NewApi {

    ResponseEntity<Void> createEntity(@Valid CreateNewRequest createNewRequest);


}
