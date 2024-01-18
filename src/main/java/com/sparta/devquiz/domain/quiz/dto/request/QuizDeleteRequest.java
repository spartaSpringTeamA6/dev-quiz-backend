package com.sparta.devquiz.domain.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuizDeleteRequest {

    @Schema(description = "퀴즈의 id", defaultValue = "1")
    private Long id;
}
