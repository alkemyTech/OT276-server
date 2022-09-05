package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface CategoryApi {

    ResponseEntity<Void> createCategory(@Valid CreateCategoryRequest createCategoryRequest);

    @Operation(summary = "Delete category", description = "Delete category", responses = {
            @ApiResponse(responseCode = "204", description = "No content")})
    void deleteCategoryById(@NotNull Long id);
}
