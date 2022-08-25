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
public class MemberRequest {


    @NotBlank(message = "Name cannot be null")
    @JsonProperty("name")
    private String name;

    @JsonProperty("facebook_url")
    private String facebookUrl;

    @JsonProperty("instagram_url")
    private String instagramUrl;

    @JsonProperty("linkedin_url")
    private String linkedinUrl;

    @NotNull(message = "Image cannot be null")
    @JsonProperty("image")
    private String image;

    @JsonProperty("description")
    private String description;
}
