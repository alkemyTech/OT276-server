package com.alkemy.ong.ports.input.rs.response;

import com.alkemy.ong.core.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewResponse {

    private Long id;

    private String name;

    private String description;

    private String image;

    private Category category;
}
