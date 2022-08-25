package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.api.UserApi;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.USERS_URI;

@RestController
@RequestMapping(USERS_URI)
@RequiredArgsConstructor
public class UserController implements UserApi {


    private final UserService userService;

    private final UserControllerMapper mapper;


    @Override
    @PostMapping("/auth/register")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest userRequest) {

        User user = mapper.createUserRequestToUser(userRequest);

        final long id = userService.createEntity(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(location).build();
    }
}
