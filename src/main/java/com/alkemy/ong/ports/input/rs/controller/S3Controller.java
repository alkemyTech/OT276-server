package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.ports.input.rs.mapper.S3ControllerMapper;
import com.alkemy.ong.ports.output.s3.S3ServiceImpl;
import com.amazonaws.services.kafka.model.S3;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.ALKYMERS_URI;

@RestController
@RequestMapping(ALKYMERS_URI) //preguntar que poner ahi
@RequiredArgsConstructor
public class S3Controller {

    private final S3ServiceImpl service;

    private final S3ControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createS3(@Valid @RequestBody MultipartFile multipartFile) {

        S3 s3 = mapper.createAlkymerRequestToAlkymer(multipartFile);

        final long id = service.uploadFile(multipartFile);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        String url = service.uploadFile(multipartFile);

        URI location = URI.create(url);

        return ResponseEntity.created(location).build();
    }
}
