package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.USERS_URI;

@RestController
@RequestMapping(USERS_URI)
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserControllerMapper mapper;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserInformation(@AuthenticationPrincipal User user) {
        UserResponse userResponse = mapper.userToUserResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {

        List<User> list = service.getList();
        List<UserResponse> response = mapper.userListToUserResponseList(list);

        return ResponseEntity.ok().body(response);
    }

}
