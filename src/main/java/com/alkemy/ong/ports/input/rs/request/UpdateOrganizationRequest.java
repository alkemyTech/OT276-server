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

    @JsonProperty("welcomeText")
    private String welcomeText;

    @JsonProperty("aboutUsText")
    private String aboutUsText;

    @JsonProperty("facebookUrl")
    private String facebookUrl;

    @JsonProperty("linkedinUrl")
    private String linkedinUrl;

    @JsonProperty("instagramUrl")
    private String instagramUrl;
}
