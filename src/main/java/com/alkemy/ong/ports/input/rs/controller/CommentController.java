package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.CommentList;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.CommentService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.CommentApi;
import com.alkemy.ong.ports.input.rs.mapper.CommentControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import com.alkemy.ong.ports.input.rs.response.CommentResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.COMMENTS_URI;

@RestController
@RequestMapping(COMMENTS_URI)
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;

    private final CommentControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createEntity(@Valid @RequestBody CreateCommentRequest createCommentRequest, @AuthenticationPrincipal User user) {

        Comment comment = mapper.createCommentRequestToComment(createCommentRequest, user);

        Long id = commentService.createEntity(comment, createCommentRequest.getNewId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        commentService.deleteEntityById(id, user);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCommentIfExists(@Valid @RequestBody CreateCommentRequest createCommentRequest, @PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        Comment comment = mapper.createCommentRequestToComment(createCommentRequest);
        commentService.updateEntityIfExists(id, comment, createCommentRequest.getNewId(), user);
    }

    @Override
    @GetMapping
    public ResponseEntity<CommentResponseList> getComments(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        CommentList list = commentService.getList(PageRequest.of(pageNumber, pageSize));

        CommentResponseList commentResponseList;
        {
            commentResponseList = new CommentResponseList();

            List<CommentResponse> content = mapper.commentListToCommentResponseList(list.getContent());
            commentResponseList.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            commentResponseList.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            commentResponseList.setPreviousUri(ApiConstants.uriByPageAsString.apply(previousPage));

            commentResponseList.setTotalPages(list.getTotalPages());
            commentResponseList.setTotalElements(list.getTotalElements());
        }

        return ResponseEntity.ok().body(commentResponseList);
    }
}
