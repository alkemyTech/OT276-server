package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import com.sun.istack.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;


@Validated
public interface SlideApi {

    ResponseEntity<SlideResponse> getById(@NotNull Long id);
}
