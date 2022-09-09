package com.alkemy.ong.ports.input.rs.controller;


import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.usecase.CategoryService;
import com.alkemy.ong.ports.input.rs.api.CategoryApi;
import com.alkemy.ong.ports.input.rs.mapper.CategoryMapperController;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import com.alkemy.ong.ports.input.rs.request.UpdateCategoryRequest;
import com.alkemy.ong.ports.input.rs.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.CATEGORIES_URI;


@RestController
@RequestMapping(CATEGORIES_URI)
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    private final CategoryMapperController mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {

        Category category = mapper.createCategoryRequestToCategory(createCategoryRequest);

        final long id = categoryService.createEntity(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@NotNull @PathVariable Long id) {
        Category category = categoryService.getByIdIfExists(id);
        CategoryResponse response = mapper.categoryToCategoryResponse(category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategory(@NotNull @PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        Category category = mapper.updateCategoryRequestToCategory(updateCategoryRequest);
        categoryService.updateEntityIfExists(id, category);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@NotNull @PathVariable("id") Long id) {
        categoryService.deleteById(id);
    }

}
