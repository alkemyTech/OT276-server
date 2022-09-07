package com.alkemy.ong.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestimonialRequest {



    @NotEmpty
    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    @NotEmpty
    @JsonProperty("content")
    private String content;
}
