package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.security.JwtUtils;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.api.UserApi;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.request.LoginRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.AUTHENTICATION_URI;

@RestController
@RequestMapping(AUTHENTICATION_URI)
@RequiredArgsConstructor
public class AuthController implements UserApi {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserService userService;

    private final UserControllerMapper mapper;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails user) {
            String jwt = jwtUtils.generateToken(user);
            Date expiration = jwtUtils.extractExpiration(jwt);
            return AuthenticationResponse.builder()
                    .token(jwt)
                    .expirationDate(expiration)
                    .build();
        }
        throw new AccessDeniedException("error in the authentication process");

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(CreateUserRequest userRequest) {
        if (userService.exist(userRequest.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        User user = mapper.createUserRequestToUser(userRequest);
        userService.createEntity(user);
        return new ResponseEntity<>(mapper.userToUserResponse(user), HttpStatus.CREATED);
    }


}
