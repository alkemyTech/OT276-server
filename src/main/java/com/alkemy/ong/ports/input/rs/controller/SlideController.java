package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.api.SlideApi;
import com.alkemy.ong.ports.input.rs.mapper.SlideControllerMapper;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

}
