package com.alkemy.ong.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactRequest {

    @NotBlank(message = "Complete with your name")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "complete with your phone")
    @JsonProperty("phone")
    private int phone;

    @NotBlank(message = "Complete with your email")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "complete with a message")
    @JsonProperty("message")
    private String message;
}
