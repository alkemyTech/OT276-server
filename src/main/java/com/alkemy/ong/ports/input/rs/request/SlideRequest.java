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
public class SlideRequest {

    @NotBlank
    @JsonProperty("image")
    private String imageBase64;

    @JsonProperty("text")
    private String text;

    @JsonProperty("order")
    private Integer order;

    @NotNull
    @JsonProperty("organization_id")
    private Long organizationId;

}
