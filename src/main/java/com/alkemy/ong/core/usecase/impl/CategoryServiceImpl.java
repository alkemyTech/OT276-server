package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.repository.CategoryRepository;
import com.alkemy.ong.core.usecase.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public void updateEntityIfExists(Long id, Category category) {
        categoryRepository.findById(id)
                .map(categoryJpa -> {
                    Optional.ofNullable(category.getName()).ifPresent(categoryJpa::setName);

                    if (category.getDescription() != null) categoryJpa.setDescription(category.getDescription());
                    else categoryJpa.setDescription("");

                    if (category.getImage() != null) categoryJpa.setImage(category.getImage());
                    else categoryJpa.setImage("");

                    return categoryRepository.save(categoryJpa);
                }).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
    }
}
