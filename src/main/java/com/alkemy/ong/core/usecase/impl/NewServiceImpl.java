package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.repository.NewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewServiceImpl {

    private final NewRepository newRepository;

    public Long createEntity(New news){
        return newRepository.save(news).getId();
    }

}
