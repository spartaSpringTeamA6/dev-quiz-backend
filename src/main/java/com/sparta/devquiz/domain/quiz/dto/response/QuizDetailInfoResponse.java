package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "단일 퀴즈 조회 응답 dto")
public class QuizDetailInfoResponse {

    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private final Long id;

    @Schema(description = "카테고리", defaultValue = "JAVA")
    private final QuizCategory category;

    @Schema(description = "퀴즈 문제 내용", defaultValue = "Java의 주요 특징으로 올바르지 않은 것은?")
    private final String question;

    @Schema(description = "퀴즈 보기 내용")
    private final String[] example;

    @Schema(description = "퀴즈의 정답", defaultValue = "3")
    private final String answer;

    public static QuizDetailInfoResponse of(Quiz quiz) {
        return QuizDetailInfoResponse.builder()
                .id(quiz.getId())
                .category(quiz.getCategory())
                .question(quiz.getQuestion())
                .example(quiz.getExample().split("\n"))
                .answer(quiz.getAnswer())
                .build();
    }
}
