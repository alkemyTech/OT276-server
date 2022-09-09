package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.repository.CommentRepository;
import com.alkemy.ong.core.repository.NewRepository;
import com.alkemy.ong.core.usecase.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final NewRepository newRepository;

    @Override
    @Transactional
    public Long createEntity(Comment comment, Long newId) {

        New _new = newRepository.findById(newId).orElseThrow(() -> new NotFoundException(newId));
        comment.set_new(_new);
        return commentRepository.save(comment).getId();
    }
}
