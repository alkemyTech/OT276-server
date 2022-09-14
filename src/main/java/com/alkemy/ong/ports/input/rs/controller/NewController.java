package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.usecase.NewService;
import com.alkemy.ong.ports.input.rs.api.NewApi;
import com.alkemy.ong.ports.input.rs.mapper.NewControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.NEWS_URI;

@RestController
@RequestMapping(NEWS_URI)
@RequiredArgsConstructor
public class NewController implements NewApi {

    private final NewService service;

    private final NewControllerMapper mapper;


    @PostMapping("/news")
    public ResponseEntity<Void> createNew(@Valid @RequestBody CreateNewRequest createNewRequest) {

        New news = mapper.createNewRequestToNew(createNewRequest);

        final long id = service.createEntity(news,createNewRequest.getCategoryId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }


}
