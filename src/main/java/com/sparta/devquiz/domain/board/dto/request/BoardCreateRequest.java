package com.sparta.devquiz.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "보드 생성 요청 dto")
public class BoardCreateRequest {

    @Size(min = 5, max = 50, message = "제목은 최소 5자, 최대 50자 까지 작성 가능합니다.")
    @Schema(description = "제목")
    @NotBlank(message = "제목을 작성해주세요.")
    private String title;

    @Size(min = 5, max = 500, message = "내용은 최소 5자, 최대 500자 까지 작성 가능합니다.")
    @Schema(description = "내용")
    @NotBlank(message = "내용을 작성해주세요.")
    private String content;

}
