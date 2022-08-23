package com.alkemy.ong.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.ong.core.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}