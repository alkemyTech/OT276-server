package com.alkemy.ong.ports.input.rs.api;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public interface S3Api {
    ResponseEntity<Void> createS3(@NotNull @RequestBody MultipartFile file);

}
