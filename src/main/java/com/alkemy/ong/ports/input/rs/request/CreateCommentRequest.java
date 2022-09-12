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
public class CreateCommentRequest {

    @NotNull(message = "El campo post_id no debe ser nulo")
    @JsonProperty("post_id")
    private Long newId;

    @NotBlank(message = "El campo body no debe estar vacio o nulo")
    @JsonProperty("body")
    private String body;

}
