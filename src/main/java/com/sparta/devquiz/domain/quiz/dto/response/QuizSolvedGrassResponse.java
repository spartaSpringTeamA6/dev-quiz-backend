package com.sparta.devquiz.domain.quiz.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.NumberExpression;
import com.sparta.devquiz.domain.user.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

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
