package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Organization;
import com.alkemy.ong.core.repository.OrganizationRepository;
import com.alkemy.ong.core.usecase.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional(readOnly = true)
    public Organization getOrganizationEntity(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void updateEntityIfExists(Long id, Organization organization) {
        organizationRepository.findById(id)
                .map(organizationEntity -> {
                    Optional.ofNullable(organization.getImage()).ifPresent(organizationEntity::setImage);
                    Optional.ofNullable(organization.getAddress()).ifPresent(organizationEntity::setAddress);
                    Optional.ofNullable(organization.getPhone()).ifPresent(organizationEntity::setPhone);
                    Optional.ofNullable(organization.getEmail()).ifPresent(organizationEntity::setEmail);
                    Optional.ofNullable(organization.getWelcomeText()).ifPresent(organizationEntity::setWelcomeText);
                    Optional.ofNullable(organization.getAboutUsText()).ifPresent(organizationEntity::setAboutUsText);
                    Optional.ofNullable(organization.getFacebookUrl()).ifPresent(organizationEntity::setFacebookUrl);
                    Optional.ofNullable(organization.getLinkedinUrl()).ifPresent(organizationEntity::setLinkedinUrl);
                    Optional.ofNullable(organization.getInstagramUrl()).ifPresent(organizationEntity::setInstagramUrl);
                    return organizationRepository.save(organizationEntity);
                })
                .orElseThrow(()->new NotFoundException(id));
    }
}
