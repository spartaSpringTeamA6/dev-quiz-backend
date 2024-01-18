package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "단일 퀴즈 간략 조회 응답 DTO")
public class QuizInfoResponse {
    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private final Long id;

    @Schema(description = "카테고리", defaultValue = "JAVA")
    private final QuizCategory category;

    @Schema(description = "퀴즈 문제 내용", defaultValue = "Java의 주요 특징으로 올바르지 않은 것은?")
    private final String question;

}
