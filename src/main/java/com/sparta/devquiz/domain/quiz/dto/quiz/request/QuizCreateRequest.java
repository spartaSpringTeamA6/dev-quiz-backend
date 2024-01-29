package com.sparta.devquiz.domain.quiz.dto.quiz.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter

@Schema(description = "퀴즈 생성 요청 dto")
public class QuizCreateRequest {

    @Schema(description = "퀴즈의 카테고리", defaultValue = "JAVA")
    @NotNull(message = "카테고리 타이틀은 필수입니다.")
    private String categoryTitle;

    @Schema(description = "퀴즈의 제목", defaultValue = "Java의 주요 특징으로 올바르지 않은 것은?")
    @NotBlank(message = "퀴즈 제목은 비워둘 수 없습니다.")
    private String quizTitle;

    private List<Choice> choices;

    @Getter
    @Schema(description = "퀴즈의 보기")
    public static class Choice {

        @Schema(description = "선택지 내용", defaultValue = "?")
        @NotBlank(message = "선택지 내용은 필수입니다.")
        private String choiceContent;

        @Schema(description = "정답 여부", defaultValue = "true")
        @NotNull(message = "정답 여부가 지정되어야 합니다.")
        private boolean isAnswer;
    }
}