package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.model.SlideList;
import com.alkemy.ong.core.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.SlideApi;
import com.alkemy.ong.ports.input.rs.mapper.SlideControllerMapper;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import com.alkemy.ong.ports.input.rs.response.SlideResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<SlideResponseList> getSlide(@RequestParam Optional<Integer> page,
                                                      @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        SlideList list = slideService.getList(PageRequest.of(pageNumber, pageSize));

        SlideResponseList response;
        {
            response = new SlideResponseList();

            List<SlideResponse> content = mapper.slideListToSlideResponseList(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(ApiConstants.uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
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
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSlide(@NotNull @PathVariable Long id) {

        slideService.deleteById(id);

    }


}
