package com.sparta.devquiz.domain.comment.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeId implements Serializable {

    private Long userId;
    private Long commentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentLikeId commentLikeId = (CommentLikeId) o;
        return Objects.equals(getUserId(), commentLikeId.getUserId()) && Objects.equals(
            getCommentId(), commentLikeId.getCommentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getCommentId());
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

}
