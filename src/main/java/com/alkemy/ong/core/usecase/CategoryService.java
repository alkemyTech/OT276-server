package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.model.CategoryList;
import org.springframework.data.domain.PageRequest;

public interface CategoryService {

    Long createEntity(Category category);

    Category getByIdIfExists(Long id);

    CategoryList getList(PageRequest pageRequest);

    void updateEntityIfExists(Long id, Category entity);

    void deleteById(Long id);

}
