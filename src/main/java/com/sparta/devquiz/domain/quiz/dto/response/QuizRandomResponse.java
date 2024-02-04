package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.entity.QuizChoice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "랜덤 퀴즈에 대한 응답 dto")
public class QuizRandomResponse {
    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private Long id;

    @Schema(description = "퀴즈 타이틀", defaultValue = "Java의 주요 특징으로 올바르지 않은 것은?")
    private String quizTitle;

    @Schema(description = "퀴즈 보기 내용", defaultValue = "1. 객체지향 프로그래밍\n2. JVM 위에서 실행\n3. 포인터를 직접 다룰 수 있음\n4. 가비지 컬렉션 제공")
    private List<QuizChoice> quizChoices;

    public static QuizRandomResponse of(Quiz quiz) {
        return QuizRandomResponse.builder()
                .id(quiz.getId())
                .quizTitle(quiz.getQuizTitle())
                .quizChoices(quiz.getQuizChoices())
                .build();
    }


    public static List<QuizRandomResponse> of(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(QuizRandomResponse::of)
                .toList();
    }
}
