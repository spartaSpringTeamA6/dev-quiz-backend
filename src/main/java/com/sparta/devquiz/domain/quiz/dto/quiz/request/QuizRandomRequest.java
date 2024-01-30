package com.sparta.devquiz.domain.quiz.dto.quiz.request;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "퀴즈 랜덤 조회 요청 dto")
public class QuizRandomRequest {
    @NotNull(message = "카테고리는 필수로 입력해야 합니다.")
    @Schema(description = "랜덤 퀴즈 요청 시 선택할 퀴즈 카테고리", defaultValue = "JAVA")
    private QuizCategory category;

}
