package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface CategoryControllerMapper extends CommonMapper {

    @Named("createCategoryRequestToCategory")
    Category createCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);
}
