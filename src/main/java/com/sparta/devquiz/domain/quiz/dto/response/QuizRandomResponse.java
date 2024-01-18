package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "랜덤 퀴즈에 대한 응답 dto")
public class QuizRandomResponse {

    private Long id;

    private String question;

    private String example;


    public static QuizRandomResponse of(Quiz quiz) {
        return QuizRandomResponse.builder()
                .id(quiz.getId())
                .question(quiz.getQuestion())
                .example(quiz.getExample())
                .build();
    }

    public static List<QuizRandomResponse> of(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(QuizRandomResponse::of)
                .toList();
    }
}
