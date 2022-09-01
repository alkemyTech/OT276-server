package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface OrganizationApi {

    @Operation(summary = "get Organization", description = "get Organization", responses = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    ResponseEntity<OrganizationResponse> getOrganization(Long id);

    @Operation(summary = "get Slides filtered and order", description = "get Slides by Organization Id and order by Order", responses = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    ResponseEntity<List<SlideResponse>> getSlidesByOrganizationIdAndOrderByOrder(@NotNull Long id);

}
