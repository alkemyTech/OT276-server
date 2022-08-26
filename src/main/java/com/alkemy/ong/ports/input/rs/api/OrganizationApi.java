package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.config.exception.error.ErrorDetails;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface OrganizationApi {

    @Operation(summary = "get Organization", description = "get Organization", responses = {
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    ResponseEntity<OrganizationResponse> getOrganization(Long id);

}
