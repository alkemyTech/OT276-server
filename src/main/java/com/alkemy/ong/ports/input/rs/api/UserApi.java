package com.alkemy.ong.ports.input.rs.api;


import com.alkemy.ong.config.exception.error.ErrorDetails;
import com.alkemy.ong.ports.input.rs.request.LoginRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import org.springframework.http.ResponseEntity;


import com.alkemy.ong.core.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.request.LoginRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Validated
public interface UserApi {


    @Operation(summary = "Login user", description = "Login an exist user", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ErrorDetails.class),
            examples =@ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\",\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))})
    })
    AuthenticationResponse login(@Valid @RequestBody LoginRequest request);

    @Operation(summary = "Register user", description = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = )
    })

    ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest userRequest);

    @Operation(summary = "Register user", description = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = )
    })


    ResponseEntity<UserResponse> getUserInformation(@AuthenticationPrincipal User user);
    

}
