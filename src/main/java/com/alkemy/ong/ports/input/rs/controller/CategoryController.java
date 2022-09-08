package com.alkemy.ong.ports.input.rs.controller;


import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.usecase.CategoryService;
import com.alkemy.ong.ports.input.rs.api.CategoryApi;
import com.alkemy.ong.ports.input.rs.mapper.CategoryControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    private final CategoryControllerMapper mapper;

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

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@NotNull @PathVariable("id") Long id) {
        categoryService.deleteById(id);
    }
}
