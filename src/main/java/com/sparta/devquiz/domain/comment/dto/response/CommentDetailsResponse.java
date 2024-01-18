package com.sparta.devquiz.domain.comment.dto.response;

import com.sparta.devquiz.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "댓글 정보 응답 Dto")
public class CommentDetailsResponse {

    @Column
    @Schema(description = "댓글 내용", defaultValue = "모두 키보드에서 손 떼!")
    private String content;

    public static CommentDetailsResponse of(Comment comment) {
        return CommentDetailsResponse
                .builder()
                .content(comment.getContent())
                .build();
    }

    public static List<CommentDetailsResponse> of(List<Comment> commentList) {
        return commentList
                .stream()
                .map(CommentDetailsResponse::of)
                .toList();
    }
}