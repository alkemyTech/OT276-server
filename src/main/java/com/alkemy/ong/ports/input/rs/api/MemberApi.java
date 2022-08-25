package com.alkemy.ong.ports.input.rs.api;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface MemberApi {



}
