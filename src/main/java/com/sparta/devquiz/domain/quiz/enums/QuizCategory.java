package com.sparta.devquiz.domain.quiz.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizCategory {

    COMPUTER_SCIENCE("COMPUTER_SCIENCE"),
    DESIGN_PATTERN("DESIGN_PATTERN"),
    JAVA("JAVA"),
    SPRING("SPRING"),
    JPA("JPA"),
    DATABASE("DATABASE");

    private final String major;

}
