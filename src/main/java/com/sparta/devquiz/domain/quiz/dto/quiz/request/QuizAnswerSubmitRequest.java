package com.sparta.devquiz.domain.quiz.dto.quiz.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
@Schema(description = "퀴즈 답안 제출 요청 dto")
public class QuizAnswerSubmitRequest {

    @Column
    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private Long quizId;

    @Schema(description = "선택한 답안의 보기 ID", defaultValue = "3")
    private Long choiceId;
}

