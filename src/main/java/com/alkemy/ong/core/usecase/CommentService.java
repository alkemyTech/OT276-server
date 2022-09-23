package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.CommentList;
import com.alkemy.ong.core.model.User;
import org.springframework.data.domain.PageRequest;

public interface CommentService {

    Long createEntity(Comment entity, Long newId);

    void deleteEntityById(Long id, User user);

    void updateEntityIfExists(Long id, Comment comment, Long newId, User user);

    CommentList getList(PageRequest pageRequest);
}
