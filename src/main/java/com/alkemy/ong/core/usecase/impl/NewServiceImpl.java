package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.repository.CategoryRepository;
import com.alkemy.ong.core.repository.NewRepository;
import com.alkemy.ong.core.usecase.NewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewServiceImpl implements NewService {

    private final NewRepository newRepository;

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long createEntity(New _new, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(categoryId));
        _new.setCategory(category);

        return newRepository.save(_new).getId();
    }
}