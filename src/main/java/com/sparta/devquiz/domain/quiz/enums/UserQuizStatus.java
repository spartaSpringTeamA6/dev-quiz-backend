package com.sparta.devquiz.domain.quiz.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserQuizStatus {

    FIRST("FIRST", 20,"오늘 처음 푼 문제입니다!"),
    CORRECT("CORRECT", 10,"정답입니다!"),
    FAIL("FAIL", 5,"오답입니다!"),
    PASS("PASS", 0,"넘어갑니다!");

    private final String status;
    private final int score;
    private final String message;
}
