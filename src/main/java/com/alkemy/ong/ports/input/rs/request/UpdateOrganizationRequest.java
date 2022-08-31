package com.alkemy.ong.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrganizationRequest {

    @JsonProperty("image")
    private String image;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private int phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("welcome_text")
    private String welcomeText;

    @JsonProperty("about_us")
    private String aboutUsText;

    @JsonProperty("facebook_url")
    private String facebookUrl;

    @JsonProperty("linkedin_url")
    private String linkedinUrl;

    @JsonProperty("instagram_url")
    private String instagramUrl;
}
