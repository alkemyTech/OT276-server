package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface CommentApi {

    @Operation(summary = "Create new Comment", description = "Create new Comment", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    @Parameter(name = "user", hidden = true)
    ResponseEntity<Void> createEntity(@Valid CreateCommentRequest createCommentRequest, @AuthenticationPrincipal User user);

    @Operation(summary = "Delete comment", description = "Delete comment", responses = {
            @ApiResponse(responseCode = "204", description = "No content")})
    @Parameter(name = "user", hidden = true)
    void deleteCommentById(@NotNull Long id, @AuthenticationPrincipal User user);

    @Operation(summary = "Update comment", description = "Update comment", responses = {
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @Parameter(name = "user", hidden = true)
    void updateCommentIfExists(@Valid @RequestBody CreateCommentRequest createCommentRequest, @PathVariable("id") Long id, @AuthenticationPrincipal User user);

    @Operation(summary = "Get Comment List", description = "Get Comment List", responses = {
            @ApiResponse(responseCode = "200", description = "OK")})
    ResponseEntity<CommentResponseList> getComments(Optional<Integer> page, Optional<Integer> size);
}
