package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Category;

public interface CategoryService {

    Long createEntity(Category category);

    Category getByIdIfExists(Long id);

    void deleteById(Long id);
}
