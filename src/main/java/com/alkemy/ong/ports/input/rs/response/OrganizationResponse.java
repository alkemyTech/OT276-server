package com.alkemy.ong.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponse {

    private String name;
    private String image;
    private int phone;
    private String address;
    private String facebookUrl;
    private String linkedinUrl;
    private String instagramUrl;

}
