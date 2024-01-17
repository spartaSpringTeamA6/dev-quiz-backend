package com.sparta.devquiz.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "코멘트 생성 요청 Dto")
public class CommentCreateRequest {

    @Size(max = 255)
    @Schema(description = "댓글 내용")
    private String content;
}
