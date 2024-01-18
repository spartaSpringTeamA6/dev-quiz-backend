package com.sparta.devquiz.domain.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "퀴즈 답안 제출 응답 DTO")
public class QuizAnswerSubmitResponse {

    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private final Long id;

    @Schema(description = "제출된 답안", defaultValue = "1")
    private final String submittedAnswer;

    @Schema(description = "답안의 정확성", defaultValue = "true")
    private final boolean isCorrect;

    @Schema(description = "정답", defaultValue = "1")
    private final String correctAnswer;

    @Schema(description = "답안에 대한 결과 메시지", defaultValue = "정답입니다!")
    private final String resultMessage;

}
