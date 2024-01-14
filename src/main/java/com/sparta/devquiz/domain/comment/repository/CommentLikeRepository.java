package com.sparta.devquiz.domain.comment.repository;

import com.sparta.devquiz.domain.comment.entity.CommentLike;
import com.sparta.devquiz.domain.comment.entity.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
    boolean existsById(CommentLikeId commentLikeId);

    void deleteById(CommentLikeId commentLikeId);
}
