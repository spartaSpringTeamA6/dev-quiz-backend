package com.sparta.devquiz.domain.quiz.dto.quiz.response;

import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "유저가 푼 퀴즈 조회 응답 dto")
public class QuizGetByUserResponse {

    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private Long id;

    @Schema(description = "문제 푼 날짜", defaultValue = "")
    private LocalDateTime solvedDate;

    @Schema(description = "카테고리", defaultValue = "JAVA")
    private String categoryTitle;

    @Schema(description = "상태", defaultValue = "CORRECT")
    private UserQuizStatus status;

    @Schema(description = "퀴즈의 제목", defaultValue = "Java의 주요 특징으로 올바르지 않은 것은?")
    private String quizTitle;

    @Schema(description = "퀴즈 보기 내용", defaultValue = "객체지향 프로그래밍\nJVM 위에서 실행\n포인터를 직접 다룰 수 있음\n가비지 컬렉션 제공")
    private String quizContent;

    public static QuizGetByUserResponse of(Long id, LocalDateTime solvedDate, String categoryTitle, UserQuizStatus status, String quizTitle, String quizContent) {
        return QuizGetByUserResponse.builder()
                .id(id)
                .solvedDate(solvedDate)
                .categoryTitle(categoryTitle)
                .status(status)
                .quizTitle(quizTitle)
                .quizContent(quizContent)
                .build();
    }
}
