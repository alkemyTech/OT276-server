package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Organization;
import com.alkemy.ong.core.repository.OrganizationRepository;
import com.alkemy.ong.core.usecase.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repoOrganization;

    @Override
    public Organization getOrganizationEntity(Long id) {
        return repoOrganization.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
