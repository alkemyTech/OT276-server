package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Comment;

public interface CommentService {

    Long createEntity(Comment entity, Long idNew);
}
