package com.alkemy.ong.core.usecase;

import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.model.NewList;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface NewService {

    List<Comment> getComments(Long id);

    Long createEntity(New _new, Long categoryId);

    NewList getList(PageRequest pageRequest);
}
