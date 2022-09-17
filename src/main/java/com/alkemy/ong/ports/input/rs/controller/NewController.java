package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.AlkymerList;
import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.model.NewList;
import com.alkemy.ong.core.usecase.NewService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.NewApi;
import com.alkemy.ong.ports.input.rs.mapper.NewControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponse;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponseList;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import com.alkemy.ong.ports.input.rs.response.NewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.NEWS_URI;

@RestController
@RequestMapping(NEWS_URI)
@RequiredArgsConstructor
public class NewController implements NewApi {

    private final NewService newService;

    private final NewControllerMapper mapper;


    @Override
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@NotNull @PathVariable Long id) {

        List<CommentResponse> response = mapper.commentListToCommentResponseList(newService.getComments(id));

        return ResponseEntity.ok(response);
    }


    @Override
    @PostMapping("")
    public ResponseEntity<Void> createEntity(@Valid @RequestBody CreateNewRequest createNewRequest) {

        New _new= mapper.createNewRequestToNew(createNewRequest);

        final long id = newService.createEntity(_new,createNewRequest.getCategoryId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<NewResponse> getNew(@NotNull @PathVariable Long id) {
        New news = newService.getByIdIfExists(id);
        NewResponse response = mapper.newToNewResponse(news);
        return ResponseEntity.ok(response);
    }
    

    @Override
    @GetMapping
    public ResponseEntity<NewResponseList> getNew(@RequestParam Optional<Integer> page,
    @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        NewList list = newService.getList(PageRequest.of(pageNumber, pageSize));

        AlkymerResponseList response;
        {
            response = new NewResponseList();

            List<NewResponse> content = mapper.newListToNewResponse(list.getContent());
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
}
