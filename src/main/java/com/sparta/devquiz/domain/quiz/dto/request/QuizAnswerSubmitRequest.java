package com.sparta.devquiz.domain.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
@Schema(description = "퀴즈 답안 제출 요청 dto")
public class QuizAnswerSubmitRequest {

    @Schema(description = "퀴즈의 id", defaultValue = "1")
    private Long id;

    @Column
    @Schema(description = "퀴즈의 정답", defaultValue = "1")
    private String answer;
}
