package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.model.NewList;
import com.alkemy.ong.core.repository.CategoryRepository;
import com.alkemy.ong.core.repository.NewRepository;
import com.alkemy.ong.core.usecase.NewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewServiceImpl implements NewService {

    private final NewRepository newRepository;

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getComments(Long id) {

        return newRepository.findById(id).orElseThrow(() -> new NotFoundException(id)).getComments().stream().toList();
    }


    @Override
    @Transactional
    public Long createEntity(New _new, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(categoryId));
        _new.setCategory(category);

        return newRepository.save(_new).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public NewList getList(PageRequest pageRequest) {
        Page<New> page = newRepository.findAll(pageRequest);
        return new NewList(page.getContent(), pageRequest, page.getTotalElements());
    }
}
