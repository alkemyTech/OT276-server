package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
@Validated
public interface NewApi {

    ResponseEntity<Void> createNew(@Valid @RequestBody CreateNewRequest createNewRequest);
}
