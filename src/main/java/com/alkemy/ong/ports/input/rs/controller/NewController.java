package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.usecase.NewService;
import com.alkemy.ong.ports.input.rs.api.NewApi;
import com.alkemy.ong.ports.input.rs.mapper.NewControllerMapper;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

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
}
