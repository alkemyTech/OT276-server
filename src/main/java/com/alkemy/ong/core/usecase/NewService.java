package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Comment;

import java.util.List;

public interface NewService {

    List<Comment> getComments(Long id);
}
