package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import com.sun.istack.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Validated
public interface SlideApi {

    ResponseEntity<SlideResponse> getById(@NotNull Long id);

    @PostMapping
    ResponseEntity<Void> createSlide(@Valid @RequestBody SlideRequest slideRequest);
}
