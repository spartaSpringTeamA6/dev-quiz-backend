package com.sparta.devquiz.domain.quiz.dto.quiz.response;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.entity.QuizChoice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "단일 퀴즈 조회 응답 dto")
public class QuizDetailInfoResponse {

    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private final Long id;

    @Schema(description = "퀴즈 카테고리", defaultValue = "JAVA")
    private String categoryTitle;

    @Schema(description = "퀴즈의 제목", defaultValue = "Java의 주요 특징으로 올바르지 않은 것은?")
    private String quizTitle;

    @Schema(description = "퀴즈의 보기", defaultValue = "객체지향 프로그래밍\nJVM 위에서 실행\n포인터를 직접 다룰 수 있음\n가비지 컬렉션 제공")
    private String quizContent;


    public static QuizDetailInfoResponse of(Quiz quiz, QuizChoice quizChoice) {
        return QuizDetailInfoResponse.builder()
                .id(quiz.getId())
                .categoryTitle(quiz.findCategoryName())
                .quizTitle(quiz.getQuizTitle())
                .quizContent(quizChoice.getChoiceContent())
                .build();
    }
}
