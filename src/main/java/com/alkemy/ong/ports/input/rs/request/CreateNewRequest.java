package com.alkemy.ong.ports.input.rs.request;

import com.alkemy.ong.core.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewRequest {

    @NotBlank(message = "El nombre no debe estar vacio.")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "El contenido no debe estar vacio.")
    @JsonProperty("content")
    private String content;

    @NotBlank(message = "La image no debe estar vacio.")
    @JsonProperty("image")
    private String image;

    @NotNull(message = "La categoria no puede ser nula.")
    @JsonProperty("category")
    private Long categoryId;
}
