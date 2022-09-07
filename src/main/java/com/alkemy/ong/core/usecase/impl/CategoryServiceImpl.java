package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
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

    @Override
    @Transactional(readOnly = true)
    public Category getByIdIfExists(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
    }
}
