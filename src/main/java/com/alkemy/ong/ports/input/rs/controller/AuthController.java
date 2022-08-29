package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.exception.error.ErrorCode;
import com.alkemy.ong.config.exception.error.ErrorDetails;
import com.alkemy.ong.config.security.JwtUtils;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.usecase.UserService;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.AuthenticationRequest;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest userRequest) {
        if(userService.loadUserByUsername(userRequest.getEmail())!=null){
            return ResponseEntity.badRequest().build();
        }else{
        User user = mapper.createUserRequestToUser(userRequest);
        userService.createEntity(user);
        return new ResponseEntity<>(mapper.userToUserResponse(user), HttpStatus.CREATED);
        }

    }
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect email or password", e);
        }
        final UserDetails service = userService.loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtUtils.generateToken(service);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }



}
