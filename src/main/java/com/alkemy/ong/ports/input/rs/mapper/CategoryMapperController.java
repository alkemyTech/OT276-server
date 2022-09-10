package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import com.alkemy.ong.ports.input.rs.request.UpdateCategoryRequest;
import com.alkemy.ong.ports.input.rs.response.CategoryResponse;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface CategoryMapperController extends CommonMapper {

    @Named("createCategoryRequestToCategory")
    Category createCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    @Named("updateCategoryRequestToCategory")
    Category updateCategoryRequestToCategory(UpdateCategoryRequest updateCategoryRequest);

    @Name("CategoryToCategoryResponse")
    CategoryResponse categoryToCategoryResponse(Category category);

}
