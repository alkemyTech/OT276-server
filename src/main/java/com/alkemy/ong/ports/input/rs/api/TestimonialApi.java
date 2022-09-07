package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Validated
@SecurityRequirement(name = "bearerAuth")
public interface TestimonialApi {
    ResponseEntity<TestimonialResponse> createTestimonial(@Valid @RequestBody TestimonialRequest request);
}
