package com.sparta.devquiz.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "보드 생성 요청 dto")
public class BoardCreateRequest {

    @Size(max = 50, message = "제목은 최대 50자까지 작성 가능합니다.")
    @Schema(description = "제목")
    private String title;

    @Size(max = 255, message = "내용은 최대 255자까지 작성 가능합니다.")
    @Schema(description = "내용")
    private String content;

}
