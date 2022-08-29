package com.alkemy.ong.ports.input.rs.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    private String email;

    private String password;
}
