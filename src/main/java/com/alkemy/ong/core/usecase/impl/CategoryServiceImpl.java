package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.repository.CategoryRepository;
import com.alkemy.ong.core.usecase.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long createEntity(Category category) {
        return categoryRepository.save(category).getId();
    }
}
