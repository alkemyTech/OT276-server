package com.alkemy.ong.ports.input.rs.controller;


import com.alkemy.ong.core.model.Category;
import com.alkemy.ong.core.usecase.CategoryService;
import com.alkemy.ong.ports.input.rs.api.CategoryApi;
import com.alkemy.ong.ports.input.rs.mapper.CategoryMapperController;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
}