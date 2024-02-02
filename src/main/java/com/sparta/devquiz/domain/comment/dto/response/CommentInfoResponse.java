package com.sparta.devquiz.domain.comment.dto.response;

import com.sparta.devquiz.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "댓글 정보 응답 Dto")
public class CommentInfoResponse {

    @Schema(description = "댓글 번호", defaultValue = "1")
    private Long commentId;

    @Schema(description = "댓글 내용", defaultValue = "모두 키보드에서 손 떼!")
    private String content;

    @Schema(description = "댓글 좋아요 개수", defaultValue = "1")
    private int likeCount;

    public static CommentInfoResponse of(Comment comment) {
        return CommentInfoResponse
                .builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .likeCount(comment.getCommentLikeList().size())
                .build();
    }

    public static List<CommentInfoResponse> of(List<Comment> commentList) {
        return commentList
                .stream()
                .map(CommentInfoResponse::of)
                .toList();
    }
}