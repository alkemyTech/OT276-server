package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.model.CategoryList;
import org.springframework.data.domain.PageRequest;

public interface CategoryService {

    Long createEntity(Category category);

    Category getByIdIfExists(Long id);

    CategoryList getList(PageRequest pageRequest);

    void deleteById(Long id);

}
