package com.sparta.devquiz.domain.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Date;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "유저 잔디 정보 응답 dto")
public class QuizSolvedGrassResponse {

    @Schema(description = "퀴즈 푼 개수", defaultValue = "20")
    private Long value;

    @Schema(description = "퀴즈 푼 날짜", defaultValue = "2024-01-29")
    private Date day;

    public QuizSolvedGrassResponse(Long value, Date day) {
        this.value = value;
        this.day = day;
    }
}
