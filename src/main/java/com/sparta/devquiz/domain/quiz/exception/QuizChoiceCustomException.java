package com.sparta.devquiz.domain.quiz.exception;

import com.sparta.devquiz.global.exception.CustomException;
import lombok.Getter;

@Getter
public class QuizChoiceCustomException extends CustomException {


    public QuizChoiceCustomException(QuizChoiceExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(),
                exceptionCode.getMessage());
    }
}
