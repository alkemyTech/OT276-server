package com.alkemy.ong.ports.input.rs.api;


import com.alkemy.ong.ports.input.rs.request.MemberRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface MemberApi {

    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberRequest memberRequest);



