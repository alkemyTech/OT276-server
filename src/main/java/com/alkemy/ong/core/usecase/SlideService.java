package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.model.SlideList;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface SlideService {

    Slide getSlideEntity(Long id);

    List<Slide> getListByOrganizationIdAndOrderByOrder(Long id);

    long createEntity(String imageBase64, Integer order, String text, Long organizationId);

    SlideList getList(PageRequest pageRequest);
}
