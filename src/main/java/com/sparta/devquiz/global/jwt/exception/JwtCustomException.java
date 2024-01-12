package com.sparta.devquiz.global.jwt.exception;

import com.sparta.devquiz.global.exception.CustomException;
import lombok.Getter;

@Getter
public class JwtCustomException extends CustomException {
    public JwtCustomException(JwtExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(), exceptionCode.getMessage());
    }
}
