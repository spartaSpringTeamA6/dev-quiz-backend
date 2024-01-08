package com.sparta.devquiz.domain.comment.entity;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment_likes")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseTimeEntity {

    @EmbeddedId
    private CommentLikeId commentLikeId;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("commentId")
    private Comment comment;

}
