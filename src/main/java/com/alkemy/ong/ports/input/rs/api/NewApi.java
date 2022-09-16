package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponseList;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
@SecurityRequirement(name = "bearerAuth")
public interface NewApi {

    ResponseEntity<List<CommentResponse>> getComments(@NotNull Long id);

    ResponseEntity<Void> createEntity(@Valid CreateNewRequest createNewRequest);

    ResponseEntity<AlkymerResponseList> getNew(Optional<Integer> page, Optional<Integer> size);
}
