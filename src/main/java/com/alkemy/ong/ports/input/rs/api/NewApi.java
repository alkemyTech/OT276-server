package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@SecurityRequirement(name = "bearerAuth")
public interface NewApi {

    ResponseEntity<List<CommentResponse>> getComments(@NotNull Long id);

    ResponseEntity<Void> createEntity(@Valid CreateNewRequest createNewRequest);

    void deleteNew(@NotNull Long id);
}
