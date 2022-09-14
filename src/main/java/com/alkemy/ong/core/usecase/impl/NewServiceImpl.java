package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.repository.CategoryRepository;
import com.alkemy.ong.core.repository.NewRepository;
import com.alkemy.ong.core.usecase.NewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Name;

@Service
@RequiredArgsConstructor
public class NewServiceImpl implements NewService {

    private final NewRepository newRepository;

    private final CategoryRepository categoryRepository;


    @Transactional
    public Long createEntity(New news, Long categoryId){

        news.setName(news.getName());
        news.setContent(news.getContent());
        news.setCategory(categoryRepository.findById(categoryId).get());
        return newRepository.save(news).getId();
    }
}
