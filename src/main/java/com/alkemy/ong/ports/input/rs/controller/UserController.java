package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.UpdateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public void updateUser(@NotNull @PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest, @AuthenticationPrincipal User loggedUser) {

        if (loggedUser.getId() == id || loggedUser.getRole().getName().equals("ROLE_ADMIN")) {
            User user = mapper.updateUserRequestToUser(updateUserRequest);
            service.updateEntityIfExists(id, user);
        } else {
            throw new AccessDeniedException("Access denied to resource");
        }
    }

}
