package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.CommentService;
import com.alkemy.ong.ports.input.rs.api.CommentApi;
import com.alkemy.ong.ports.input.rs.mapper.CommentControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
}
