package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "유저가 푼 퀴즈 조회 응답 dto")
public class QuizGetByUserResponse {

    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private Long id;

    @Schema(description = "문제 푼 날짜", defaultValue = "")
    private LocalDateTime solvedDate;

    @Schema(description = "카테고리", defaultValue = "JAVA")
    private QuizCategory category;

    @Schema(description = "상태", defaultValue = "CORRECT")
    private UserQuizStatus status;

    @Schema(description = "퀴즈 문제 내용", defaultValue = "Java의 주요 특징으로 올바르지 않은 것은?")
    private String question;

    @Schema(description = "퀴즈 보기 내용", defaultValue = "1. 객체지향 프로그래밍\n2. JVM 위에서 실행\n3. 포인터를 직접 다룰 수 있음\n4. 가비지 컬렉션 제공")
    private String example;

    public static QuizGetByUserResponse of (UserQuiz userQuiz){
        return QuizGetByUserResponse.builder()
                .id(userQuiz.getQuiz().getId())
                .solvedDate(userQuiz.getCreatedAt())
                .category(userQuiz.getQuiz().getCategory())
                .question(userQuiz.getQuiz().getQuestion())
                .example(userQuiz.getQuiz().getExample())
                .status(userQuiz.getStatus())
                .build();
    }
}
