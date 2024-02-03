package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.entity.QuizChoice;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<QuizChoice> quizChoices;

    public static QuizGetByUserResponse of (UserQuiz userQuiz){
        return QuizGetByUserResponse.builder()
                .id(userQuiz.getQuiz().getId())
                .solvedDate(userQuiz.getCreatedAt())
                .categoryTitle(userQuiz.getQuiz().getCategory().getCategoryTitle())
                .quizTitle(userQuiz.getQuiz().getQuizTitle())
                .quizChoices(userQuiz.getQuiz().getQuizChoices())
                .status(userQuiz.getStatus())
                .build();
    }

    public static List<QuizGetByUserResponse> of(List<UserQuiz> userQuizzes){
        return userQuizzes.stream()
                .map(QuizGetByUserResponse::of)
                .toList();
    }
}
