package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.UpdateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.USERS_URI;

@RestController
@RequestMapping(USERS_URI)
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserControllerMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {

        List<User> list = service.getList();
        List<UserResponse> response = mapper.userListToUserResponseList(list);

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@NotNull @PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        User user = mapper.updateUserRequestToUser(updateUserRequest);
        service.updateEntityIfExists(id, user);
    }

}
