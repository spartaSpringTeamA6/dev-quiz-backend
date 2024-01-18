package com.sparta.devquiz.domain.comment.repository;


import com.sparta.devquiz.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardIdAndIsDeletedFalse(Long boardId);
}
