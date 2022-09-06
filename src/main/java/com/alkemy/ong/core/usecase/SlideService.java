package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Slide;

public interface SlideService {

    Slide getSlideEntity(Long id);

    long createEntity(String imageBase64, Integer order, String text, Long organizationId);
}
