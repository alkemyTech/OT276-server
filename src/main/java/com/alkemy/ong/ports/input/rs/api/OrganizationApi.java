package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface OrganizationApi {

    @Operation(summary = "get Organization", description = "get Organization", responses = {
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    ResponseEntity<OrganizationResponse> getOrganization(Long id);

}
