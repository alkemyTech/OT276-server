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

import java.util.List;
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
    @Transactional
    public long createEntity(SlideRequest slideRequest) {
        String filename = UUID.randomUUID().toString();
        final String imageUrl = s3Service.uploadFile(slideRequest.getImageBase64(), filename);
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

    @Override
    @Transactional(readOnly = true)
    public List<Slide> getListByOrganizationIdAndOrderByOrder(Long id) {

        organizationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return slideRepository.findByOrganizationIdOrderByOrder(id);
    }

}
