package com.sparta.devquiz.domain.quiz.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum QuizExceptionCode {

    // BAD REQUEST 400

    // NOT FOUND 404
    NOT_FOUND_QUIZ(HttpStatus.NOT_FOUND, "QUIZ-001", "해당 퀴즈를 찾을 수 없습니다."),
    Enough_Quiz(HttpStatus.NOT_FOUND, "QUIZ-002", "퀴즈가 충분하지 않습니다.")
    // FORBIDDEN 403

    ;
    // CONFLICT 409

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
