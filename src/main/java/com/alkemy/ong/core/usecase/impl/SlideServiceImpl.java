package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Organization;
import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.repository.OrganizationRepository;
import com.alkemy.ong.core.repository.SlideRepository;
import com.alkemy.ong.core.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.output.s3.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    @Transactional
    public long createEntity(SlideRequest slideRequest) {
        final String IMG_NAME = "img_tmp";
        final String imageUrl = s3Service.uploadFile(slideRequest.getImageBase64(), IMG_NAME);
        Slide slide = new Slide();
        Organization organization = organizationRepository.findById(slideRequest.getOrganization().getId())
                .orElseThrow(() -> new NotFoundException(slideRequest.getOrganization().getId()));

        if (slideRequest.getOrder() == null) {

            slide.setOrder(slideRepository.findFirstByOrderByIdDesc().stream().findFirst().
                    orElseThrow(() -> new NotFoundException("order")).getOrder());

        } else {
            slide.setOrder(slideRequest.getOrder());
        }

        slide.setOrganization(organization);
        slide.setText(slideRequest.getText());
        slide.setImageUrl(imageUrl);

        return slideRepository.save(slide).getId();
    }

}
