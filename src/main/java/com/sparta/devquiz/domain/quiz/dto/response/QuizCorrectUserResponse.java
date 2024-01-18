package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class QuizCorrectUserResponse {
    private final Long id;
    private final String question;
    private final String example;
    private final String answer;
    private final QuizCategory category;
}
