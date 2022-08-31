package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface OrganizationApi {

    @Operation(summary = "get Organization", description = "get Organization", responses = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    ResponseEntity<OrganizationResponse> getOrganization(Long id);

    void updateOrganization(@NotNull Long id, @Valid UpdateOrganizationRequest updateOrganizationRequest);
}
