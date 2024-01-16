package com.sparta.devquiz.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter

public class CommentUpdateRequest {

    @Size(max = 255)
    @Schema(description = "댓글 내용")
    private String content;
}
