package com.alkemy.ong.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @JsonProperty("first_Name")
    private String firstName;

    @JsonProperty("last_Name")
    private String lastName;

    @Size(min = 8, max = 16)
    private String password;

    private String photo;

}
