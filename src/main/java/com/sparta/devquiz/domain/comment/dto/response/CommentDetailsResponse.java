package com.sparta.devquiz.domain.comment.dto.response;

import com.sparta.devquiz.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "댓글 정보 디테일 응답 Dto")
public class CommentDetailsResponse {

    @Column
    @Schema(description = "댓글 번호", defaultValue = "1")
    private Long commentId;

    @Column
    @Schema(description = "댓글 작성자", defaultValue = "잼민이")
    private String username;

    @Column
    @Schema(description = "댓글 내용", defaultValue = "모두 키보드에서 손 떼!")
    private String content;

    @Column
    @Schema(description = "댓글 좋아요 개수", defaultValue = "1")
    private int likeCount;

    public static CommentDetailsResponse of(Comment comment) {
        return CommentDetailsResponse
                .builder()
                .commentId(comment.getId())
                .username(comment.getUser().getUsername())
                .content(comment.getContent())
                .likeCount(comment.getCommentLikeList().size())
                .build();
    }

    public static List<CommentDetailsResponse> of(List<Comment> commentList) {
        return commentList
                .stream()
                .map(CommentDetailsResponse::of)
                .toList();
    }
}