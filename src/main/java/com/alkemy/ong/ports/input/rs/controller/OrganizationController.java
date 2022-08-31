package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Organization;
import com.alkemy.ong.core.usecase.OrganizationService;
import com.alkemy.ong.ports.input.rs.api.OrganizationApi;
import com.alkemy.ong.ports.input.rs.mapper.OrganizationControllerMapper;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.ORGANIZATIONS_URI;

@RestController
@RequestMapping(ORGANIZATIONS_URI)
@RequiredArgsConstructor
public class OrganizationController implements OrganizationApi {

    private final OrganizationService organizationService;

    private final OrganizationControllerMapper mapper;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> getOrganization(@PathVariable("id") Long id) {
        Organization organization = organizationService.getOrganizationEntity(id);
        OrganizationResponse organizationResponse = mapper.organizationToOrganizationResponse(organization);
        return ResponseEntity.ok(organizationResponse);
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrganizacion(@PathVariable("id") Long id,@RequestBody UpdateOrganizationRequest updateOrganizationRequest) {
        Organization organization = mapper.updateOrganizationRequestToOrganization(updateOrganizationRequest);
        organizationService.updateEntityIfExists(id, organization);
    }

}
