package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.AUTHENTICATION_URI;

@RestController
@RequestMapping(AUTHENTICATION_URI)
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final UserControllerMapper mapper;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest userRequest) {
        User user = mapper.createUserRequestToUser(userRequest);
        userService.createEntity(user);
        return new ResponseEntity<>(mapper.userToUserResponse(user), HttpStatus.CREATED);

    }


}
