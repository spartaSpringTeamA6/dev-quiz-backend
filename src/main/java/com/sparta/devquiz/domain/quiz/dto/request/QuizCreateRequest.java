package com.sparta.devquiz.domain.quiz.dto.request;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "퀴즈 생성 요청 dto")
public class QuizCreateRequest {

    @Schema(description = "퀴즈의 카테고리", defaultValue = "JAVA")
    @NotNull(message = "카테고리는 필수로 입력해야 합니다.")
    private QuizCategory category;

    @Schema(description = "퀴즈의 문제", defaultValue = "Java에서 static 키워드의 주요 기능은 무엇입니까?")
    @NotBlank(message = "문제를 입력해야 합니다.")
    private String question;

    @Schema(description = "퀴즈의 보기")
    @NotBlank(message = "보기를 선택해야 합니다.")
    private String[] example;

    @Schema(description = "퀴즈의 정답", defaultValue = "1")
    @NotBlank(message = "정답을 선택해야 합니다.")
    private String answer;
}