package com.alkemy.ong.ports.input.rs.api;

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

    AuthenticationResponse login(@Valid @RequestBody LoginRequest request);

    ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest userRequest);

    ResponseEntity<UserResponse> getUserInformation(@AuthenticationPrincipal User user);
    
}
