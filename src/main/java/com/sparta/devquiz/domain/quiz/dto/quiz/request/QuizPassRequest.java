package com.sparta.devquiz.domain.quiz.dto.quiz.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "퀴즈 답안 패스 요청 dto")
public class QuizPassRequest {

        @Schema(description = "퀴즈 ID", defaultValue = "1")
        private Long quizId;

}
