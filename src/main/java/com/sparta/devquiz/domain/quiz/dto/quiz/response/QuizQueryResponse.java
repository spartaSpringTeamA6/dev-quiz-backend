package com.sparta.devquiz.domain.quiz.dto.quiz.response;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "카테고리 별 총 퀴즈 조회 응답 dto")
public class QuizQueryResponse {

        @Schema(description = "퀴즈 ID", defaultValue = "1")
        private final Long id;

        @Schema(description = "퀴즈 타이틀", defaultValue = "Java의 주요 특징으로 올바르지 않은 것은?")
        private final String quizTitle;

        public static QuizQueryResponse of(Quiz quiz) {
                return QuizQueryResponse.builder()
                        .id(quiz.getId())
                        .quizTitle(quiz.getQuizTitle())
                        .build();
        }

        public static List<QuizQueryResponse> of(List<Quiz> quizzes) {
                return quizzes.stream()
                        .map(QuizQueryResponse::of)
                        .toList();
    }
}
