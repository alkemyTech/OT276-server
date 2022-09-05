package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;

public interface SlideService {

    Slide getSlideEntity(Long id);

    List<Slide> getListByOrganizationIdAndOrderByOrder(Long id);

    @Transactional
    long createEntity(SlideRequest slideRequest);

}
