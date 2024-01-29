package com.sparta.devquiz.domain.quiz.dto.quiz.request;

import com.sparta.devquiz.domain.quiz.dto.quiz.common.CustomQuizDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "퀴즈 생성 요청 dto")
public class QuizCreateRequest {

    @Schema(description = "퀴즈의 카테고리", defaultValue = "JAVA")
    @NotNull(message = "카테고리는 필수로 입력해야 합니다.")
    private Long categoryId;

    private List<CustomQuizDto> quizzes;

}