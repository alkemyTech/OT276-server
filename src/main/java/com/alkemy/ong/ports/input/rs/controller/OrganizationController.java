package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.Organization;
import com.alkemy.ong.core.model.Slide;
import com.alkemy.ong.core.usecase.OrganizationService;
import com.alkemy.ong.core.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.api.OrganizationApi;
import com.alkemy.ong.ports.input.rs.mapper.OrganizationControllerMapper;
import com.alkemy.ong.ports.input.rs.mapper.SlideControllerMapper;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.ORGANIZATIONS_URI;

@RestController
@RequestMapping(ORGANIZATIONS_URI)
@RequiredArgsConstructor
public class OrganizationController implements OrganizationApi {

    private final OrganizationService organizationService;
    private final OrganizationControllerMapper mapper;
    private final SlideService slideService;
    private final SlideControllerMapper slideMapper;

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
    public void updateOrganization(@PathVariable("id") Long id, @RequestBody UpdateOrganizationRequest updateOrganizationRequest) {
        Organization organization = mapper.updateOrganizationRequestToOrganization(updateOrganizationRequest);
        organizationService.updateEntityIfExists(id, organization);
    }

    @Override
    @GetMapping("/{id}/slides")
    public ResponseEntity<List<SlideResponse>> getSlidesByOrganizationIdAndOrderByOrder(@NotNull @PathVariable Long id) {

        List<Slide> list = slideService.getListByOrganizationIdAndOrderByOrder(id);
        List<SlideResponse> response = slideMapper.slideListToSlideResponseList(list);

        return ResponseEntity.ok(response);
    }

}
