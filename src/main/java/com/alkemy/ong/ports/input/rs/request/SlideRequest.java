package com.alkemy.ong.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlideRequest {

    @NotBlank
    @JsonProperty("image")
    private String imageBase64;

    @JsonProperty("text")
    private String text;

    @JsonProperty("order")
    private Integer order;

    @Valid
    @JsonProperty("organization_id")
    private OrganizationRequest organization;

}
