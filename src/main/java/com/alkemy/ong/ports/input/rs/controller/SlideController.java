package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.api.SlideApi;
import com.alkemy.ong.ports.input.rs.mapper.SlideControllerMapper;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.SLIDES_URI;

@RestController
@RequestMapping(SLIDES_URI)
@RequiredArgsConstructor
public class SlideController implements SlideApi {

    private final SlideService slideService;

    private final SlideControllerMapper mapper;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SlideResponse> getById(@PathVariable("id") Long id) {
        Slide slide = slideService.getSlideEntity(id);
        SlideResponse slideResponse = mapper.slideToSlideResponse(slide);
        return ResponseEntity.ok(slideResponse);
    }


    @Override
    @PostMapping
    public ResponseEntity<Void> createSlide(@Valid @RequestBody SlideRequest slideRequest) {

        final long id = slideService.createEntity(slideRequest.getImageBase64(),
                slideRequest.getOrder(), slideRequest.getText(), slideRequest.getOrganizationId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();


    }


    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSlide(@NotNull @PathVariable Long id, @Valid @RequestBody SlideRequest slideRequest) {

        slideService.updateEntityIfExists(id, slideRequest.getOrganizationId(), slideRequest.getImageBase64(), slideRequest.getOrder(), slideRequest.getText());

    }


}
