package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.core.model.Organization;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface OrganizationControllerMapper extends CommonMapper {

    @Named("organizationToOrganizationResponse")
    OrganizationResponse organizationToOrganizationResponse(Organization organization);

    @Named("updateOrganizationRequestToOrganization")
    Organization updateOrganizationRequestToOrganization(UpdateOrganizationRequest updateOrganizationRequest);
}
