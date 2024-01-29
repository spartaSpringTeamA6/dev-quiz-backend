package com.sparta.devquiz.domain.quiz.exception;

import com.sparta.devquiz.global.exception.CustomException;
import lombok.Getter;

@Getter
public class CategoryCustomException extends CustomException {


    public CategoryCustomException(CategoryExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(),
                exceptionCode.getMessage());
    }
}
