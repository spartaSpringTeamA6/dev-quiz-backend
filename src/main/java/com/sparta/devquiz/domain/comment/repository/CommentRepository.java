package com.sparta.devquiz.domain.comment.repository;


import com.sparta.devquiz.domain.comment.entity.Comment;
import com.sparta.devquiz.domain.comment.exception.CommentCustomException;
import com.sparta.devquiz.domain.comment.exception.CommentExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardIdAndIsDeletedFalse(Long boardId);
    @Query("select c from Comment c where c.user.id = :userId and c.isDeleted = FALSE")
    List<Comment> findAllByUserIdAndIsDeletedFalse(Long userId);

    default Comment findCommentByIdOrElseThrow(Long commentId) {
        return findById(commentId)
                .orElseThrow(() -> new CommentCustomException(CommentExceptionCode.NOT_FOUND_COMMENT));
    }
}