package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import com.alkemy.ong.ports.input.rs.response.CategoryResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface CategoryMapperController extends CommonMapper {

    @Named("createCategoryRequestToCategory")
    Category createCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    @Named("categoryToCategoryResponse")
    CategoryResponse categoryToCategoryResponse(Category category);

    @IterableMapping(qualifiedByName = "categoryToCategoryResponse")
    List<CategoryResponse> categoryListToCategoryResponseList(List<Category> categories);

}
