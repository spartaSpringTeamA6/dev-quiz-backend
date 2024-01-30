package com.sparta.devquiz.domain.quiz.dto.quiz.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "퀴즈 답안 패스 응답 dto")
public class QuizPassResponse {
    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private final Long id;

    @Schema(description = "상태", defaultValue = "PASS")
    private final String status;

    public static QuizPassResponse of(Long id) {
        return QuizPassResponse.builder()
                .id(id)
                .status("PASS")
                .build();
    }
}
