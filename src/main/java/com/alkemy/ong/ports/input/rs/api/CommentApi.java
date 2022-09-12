package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface CommentApi {

    @Operation(summary = "Create new Comment", description = "Create new Comment", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    ResponseEntity<Void> createEntity(@Valid CreateCommentRequest createCommentRequest, @AuthenticationPrincipal User user);
}
