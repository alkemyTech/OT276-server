package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.core.usecase.TestimonialService;
import com.alkemy.ong.ports.input.rs.api.TestimonialApi;
import com.alkemy.ong.ports.input.rs.mapper.TestimonialControllerMapper;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.TESTIMONIALS_URI;


@RestController
@RequestMapping(TESTIMONIALS_URI)
@RequiredArgsConstructor
public class TestimonialController implements TestimonialApi {

    private final TestimonialControllerMapper mapper;

    private final TestimonialService testimonialService;


    @Override
    @PostMapping
    public ResponseEntity<Void> createTestimonial(@Valid @RequestBody TestimonialRequest request) {
        Testimonial testimonial = mapper.testimonialRequestToEntity(request);

        final long id = testimonialService.createNewTestimonial(testimonial);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTestimonial(@NotNull @PathVariable("id") long id) {
        testimonialService.deleteTestimonial(id);
    }

}