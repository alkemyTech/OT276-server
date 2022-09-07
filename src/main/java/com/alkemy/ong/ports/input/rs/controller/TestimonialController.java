package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.TESTIMONIALS_URI;


@RestController
@RequestMapping(TESTIMONIALS_URI)
@RequiredArgsConstructor
public class TestimonialController {

    @PostMapping
    public ResponseEntity<TestimonialResponse> createTestimonial(@Valid @RequestBody TestimonialRequest request){

    }
}
