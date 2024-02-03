package com.sparta.devquiz.domain.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "퀴즈 답안 제출 요청 dto")
public class QuizAnswerSubmitRequest {

    @NotBlank
    @Schema(description = "선택된 답안의 순서")
    private int choiceSequence;

    @NotBlank
    @Schema(description = "선택된 답안의 내용")
    private String content;
}