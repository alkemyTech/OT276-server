package com.alkemy.ong.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlideResponse {

    private Long id;

    @JsonProperty("image_url")
    private String imageUrl;

    private String text;

    private Integer order;
    
}
