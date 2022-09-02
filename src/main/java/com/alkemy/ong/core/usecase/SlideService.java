package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Slide;

import java.util.List;

public interface SlideService {

    Slide getSlideEntity(Long id);

    List<Slide> getListByOrganizationIdAndOrderByOrder(Long id);
    
}
