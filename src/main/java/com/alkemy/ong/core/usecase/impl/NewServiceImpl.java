package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.repository.NewRepository;
import com.alkemy.ong.core.usecase.NewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewServiceImpl implements NewService {

    private final NewRepository newCrudRepository;

    @Override
    @Transactional(readOnly = true)
    public New getByIdIfExists(Long id) {

        return newCrudRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
