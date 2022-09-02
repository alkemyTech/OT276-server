package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.repository.SlideRepository;
import com.alkemy.ong.core.usecase.SlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

    private final SlideRepository slideRepository;

    @Override
    @Transactional(readOnly = true)
    public Slide getSlideEntity(Long id) {
        return slideRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

}
