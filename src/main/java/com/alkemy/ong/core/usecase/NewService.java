package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.New;

import java.util.List;

public interface NewService {

    List<Comment> getComments(Long id);

    Long createEntity(New _new, Long categoryId);

    New getByIdIfExists(Long id);
}
