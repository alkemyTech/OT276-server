package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.ports.output.s3.S3ServiceImpl;
import com.amazonaws.services.kafka.model.S3;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.ALKYMERS_URI;
import static com.alkemy.ong.ports.input.rs.api.ApiConstants.S3_URI;

@RestController
@RequestMapping(S3_URI)
@RequiredArgsConstructor
public class S3Controller {

    private final S3ServiceImpl service;

    @PostMapping
    public ResponseEntity<Void> createS3(@NotNull @RequestBody MultipartFile multipartFile) {

        final String url = service.uploadFile(multipartFile);

        URI location = URI.create(url);

        return ResponseEntity.created(location).build();
    }
}
