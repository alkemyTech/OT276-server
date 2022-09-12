package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface NewApi {

    ResponseEntity <List<CommentResponse>> getComments(@NotNull Long id);
}
