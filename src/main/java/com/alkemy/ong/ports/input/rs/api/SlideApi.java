package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Validated
public interface SlideApi {

    ResponseEntity<SlideResponse> getById(@NotNull Long id);

    ResponseEntity<Void> createSlide(@Valid @RequestBody SlideRequest slideRequest);

    void updateSlide(@NotNull Long id, @Valid @RequestBody SlideRequest slideRequest);
}
