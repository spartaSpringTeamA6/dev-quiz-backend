package com.sparta.devquiz.domain.quiz.dto.quiz.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "퀴즈 답안 제출 요청 dto")
public class QuizAnswerSubmitRequest {

    @Column
    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private Long quizId;

    @NotBlank
    @Schema(description = "선택된 답안의 ID")
    private Long choiceId;

    @NotBlank
    @Schema(description = "선택된 답안의 내용")
    private String content;
}