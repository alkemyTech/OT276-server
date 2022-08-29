package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.config.security.JwtUtils;
import com.alkemy.ong.ports.input.rs.request.LoginRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


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


}
