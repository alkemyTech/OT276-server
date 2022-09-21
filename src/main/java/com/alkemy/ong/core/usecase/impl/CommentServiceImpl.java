package com.alkemy.ong.core.usecase.impl;

import com.alkemy.ong.config.exception.NotFoundException;
import com.alkemy.ong.core.model.Comment;
import com.alkemy.ong.core.model.CommentList;
import com.alkemy.ong.core.model.New;
import com.alkemy.ong.core.model.User;
import com.alkemy.ong.core.repository.CommentRepository;
import com.alkemy.ong.core.repository.NewRepository;
import com.alkemy.ong.core.usecase.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.util.Objects;

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

    @Override
    @Transactional
    public void deleteEntityById(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if(Objects.equals(comment.getUser().getId(), user.getId()) || user.getRole().getName().equals("ROLE_ADMIN")) {
            commentRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("Access denied to resource");
        }
    }

    @Override
    @Transactional
    public void updateEntityIfExists(Long id, Comment comment, Long newId, User user) {
        commentRepository.findById(id)
                .map(commentJpa->{
                    if(Objects.equals(commentJpa.getUser().getId(), user.getId()) || user.getRole().getName().equals("ROLE_ADMIN")) {
                        New _new = newRepository.findById(newId).orElseThrow(() -> new NotFoundException(newId));
                        commentJpa.set_new(_new);
                        commentJpa.setBody(comment.getBody());
                        return commentRepository.save(commentJpa);
                    } else {
                        throw new AccessDeniedException("Access denied to resource");
                    }
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentList getList(PageRequest pageRequest) {
        Page<Comment> page = commentRepository.findAllByOrderByAuditCreatedAtAsc(pageRequest);
        return new CommentList(page.getContent(), pageRequest, page.getTotalElements());
    }
}
