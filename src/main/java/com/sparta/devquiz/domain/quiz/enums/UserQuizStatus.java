package com.sparta.devquiz.domain.quiz.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserQuizStatus {

    CORRECT("CORRECT"),
    FAIL("FAIL"),
    PASS("PASS");

    private final String status;

}
