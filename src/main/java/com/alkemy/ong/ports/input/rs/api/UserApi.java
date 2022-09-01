package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.request.LoginRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Validated
public interface UserApi {

    AuthenticationResponse login(@Valid @RequestBody LoginRequest request);

    AuthenticationResponse register(@Valid @RequestBody CreateUserRequest userRequest);
}
