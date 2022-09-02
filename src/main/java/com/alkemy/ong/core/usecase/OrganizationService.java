package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Organization;

public interface OrganizationService {

    Organization getOrganizationEntity(Long id);

    void updateEntityIfExists(Long id, Organization organization);
}
