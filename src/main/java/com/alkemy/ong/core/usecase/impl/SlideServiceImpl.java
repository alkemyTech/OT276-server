package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Organization;
import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.repository.OrganizationRepository;
import com.alkemy.ong.core.repository.SlideRepository;
import com.alkemy.ong.core.usecase.SlideService;
import com.alkemy.ong.ports.output.s3.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

    private final SlideRepository slideRepository;
    private final OrganizationRepository organizationRepository;
    private final S3ServiceImpl s3Service;


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

    @Override
    @Transactional
    public long createEntity(String imageBase64, Integer order, String text, Long organizationId) {
        Slide slide = new Slide();
        String filename = UUID.randomUUID().toString();
        final String imageUrl = s3Service.uploadFile(imageBase64, filename);

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException(organizationId));

        if (order != null) {
            slide.setOrder(order);
        } else {
            slide.setOrder(slideRepository.findNextMaxSlideOrder());
        }
        slide.setOrganization(organization);
        slide.setText(text);
        slide.setImageUrl(imageUrl);

        return slideRepository.save(slide).getId();
    }

}
