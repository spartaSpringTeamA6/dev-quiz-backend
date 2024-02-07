package com.sparta.devquiz.domain.quiz.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum QuizChoiceExceptionCode {
    // BAD REQUEST 400
    BAD_REQUEST_QUIZ_CHOICE(HttpStatus.BAD_REQUEST, "QUIZ-CHOICE-001", "해당 보기가 유효하지 않습니다."),
    // NOT FOUND 404
    NOT_FOUND_QUIZ_CHOICE(HttpStatus.NOT_FOUND, "QUIZ-CHOICE-002", "정답을 찾을 수 없습니다.");
    // FORBIDDEN 403

    // CONFLICT 409

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
