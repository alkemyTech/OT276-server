package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.User;

public interface CommentService {

    Long createEntity(Comment entity, Long newId);

    void deleteEntityById(Long id, User user);
}
