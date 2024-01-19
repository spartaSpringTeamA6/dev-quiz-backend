package com.sparta.devquiz.domain.comment.dto.response;

import com.sparta.devquiz.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "코멘트 생성 응답 Dto")
public class CommentCreateResponse {

    @Column
    @Schema(description = "보드 번호", defaultValue = "1")
    private Long boardId;

    @Column
    @Schema(description = "댓글 번호", defaultValue = "1")
    private Long commentId;

    @Column
    @Schema(description = "댓글 작성자", defaultValue = "잼민이")
    private String username;

    @Column
    @Schema(description = "댓글 내용", defaultValue = "모두 키보드에서 손 떼!")
    private String content;

    public static CommentCreateResponse of(Comment comment) {
        return CommentCreateResponse.builder()
                .boardId(comment.getBoard().getId())
                .commentId(comment.getId())
                .username(comment.getUser().getUsername())
                .content(comment.getContent())
                .build();
    }
}
