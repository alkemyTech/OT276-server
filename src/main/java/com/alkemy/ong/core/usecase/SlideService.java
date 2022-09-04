package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import org.springframework.transaction.annotation.Transactional;

public interface SlideService {

    Slide getSlideEntity(Long id);

    @Transactional
    long createEntity(SlideRequest slideRequest);

}
