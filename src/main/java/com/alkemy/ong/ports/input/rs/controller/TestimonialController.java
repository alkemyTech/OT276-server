package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Testimonial;
import com.alkemy.ong.core.model.TestimonialList;
import com.alkemy.ong.core.usecase.TestimonialService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.TestimonialApi;
import com.alkemy.ong.ports.input.rs.mapper.TestimonialControllerMapper;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.request.UpdateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    public void deleteTestimonial(@NotNull @PathVariable Long id ) {
        testimonialService.deleteTestimonial(id);
    }


    @Override
    @GetMapping
    public ResponseEntity<TestimonialResponseList> getTestimonialList(Optional<Integer> page, Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        TestimonialList list = testimonialService.getList(PageRequest.of(pageNumber, pageSize));

        TestimonialResponseList response;
        {
            response = new TestimonialResponseList();

            List<TestimonialResponse> content = mapper.testimonialListToTResponse(list.getContent());

            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(ApiConstants.uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialResponse> updateTestimonial(@PathVariable Long id, @RequestBody UpdateTestimonialRequest request) {
        Testimonial testimonial = mapper.updateTestimonialToEntity(request);
        testimonialService.updateTestimonialIfExist(id, testimonial);
        return new ResponseEntity<>(mapper.EntityToTestimonialResponse(testimonial), HttpStatus.OK);
    }


}



