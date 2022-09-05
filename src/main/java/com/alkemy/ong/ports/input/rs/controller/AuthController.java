package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.security.JwtUtils;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.api.UserApi;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.request.LoginRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.RegisterResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


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
        return createJwtToken(request.getUserName(), request.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody CreateUserRequest userRequest) {
        User user = mapper.createUserRequestToUser(userRequest);
        userService.createEntity(user);
        RegisterResponse response = mapper.userResponseToRegisterResponse(user);
        AuthenticationResponse auth = createJwtToken(userRequest.getEmail(), userRequest.getPassword());
        response.setAuth(auth);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private AuthenticationResponse createJwtToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails user1) {
            String jwt = jwtUtils.generateToken(user1);
            Date expiration = jwtUtils.extractExpiration(jwt);
            return AuthenticationResponse.builder().token(jwt).expirationDate(expiration).build();
        }
        throw new AccessDeniedException("error in the authentication process");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserInformation(@AuthenticationPrincipal User user) {
        UserResponse userResponse = mapper.userToUserResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


}
