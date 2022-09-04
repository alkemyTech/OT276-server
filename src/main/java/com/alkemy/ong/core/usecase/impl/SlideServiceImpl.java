package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.repository.OrganizationRepository;
import com.alkemy.ong.core.repository.SlideRepository;
import com.alkemy.ong.core.usecase.SlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

    private final SlideRepository slideRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional(readOnly = true)
    public Slide getSlideEntity(Long id) {
        return slideRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Slide> getListByOrganizationIdAndOrderByOrder(Long id) {
    
        organizationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return slideRepository.findByOrganizationIdOrderByOrder(id);
    }

}
