package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.core.repository.TestimonialRepository;
import com.alkemy.ong.ports.input.rs.api.TestimonialApi;
import com.alkemy.ong.ports.input.rs.mapper.TestimonialControllerMapper;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import com.amazonaws.services.xray.model.Http;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class TestimonialController implements TestimonialApi {

    private final TestimonialControllerMapper mapper;

    private final TestimonialRepository repository;


    @Override
    @PostMapping
    public ResponseEntity<TestimonialResponse> createTestimonial(@Valid @RequestBody TestimonialRequest request) {
        Testimonial testimonial = mapper.testimonialRequestToEntity(request);
        repository.save(testimonial);
        return new ResponseEntity(mapper.EntityToTestimonialResponse(testimonial), HttpStatus.OK);
    }
}
