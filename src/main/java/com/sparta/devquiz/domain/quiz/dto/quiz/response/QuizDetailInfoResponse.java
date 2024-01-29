package com.sparta.devquiz.domain.quiz.dto.quiz.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "단일 퀴즈 조회 응답 dto")
public class QuizDetailInfoResponse {

    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private final Long id;

    @Schema(description = "퀴즈 카테고리", defaultValue = "JAVA")
    private String categoryTitle;

    @Schema(description = "퀴즈의 제목", defaultValue = "Java의 주요 특징으로 올바르지 않은 것은?")
    private String quizTitle;

    @Schema(description = "퀴즈의 보기")


//    public static QuizDetailInfoResponse of(Quiz quiz) {
//        return QuizDetailInfoResponse.builder()
//                .id(quiz.getId())
//                .category(quiz.getCategory())
//                .question(quiz.getQuestion())
//                .example(quiz.getExample().split("\n"))
//                .answer(quiz.getAnswer())
//                .build();
//    }
}
