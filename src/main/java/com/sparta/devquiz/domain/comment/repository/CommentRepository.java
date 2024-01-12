package com.sparta.devquiz.domain.comment.repository;


import com.sparta.devquiz.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
