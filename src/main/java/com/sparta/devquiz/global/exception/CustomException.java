package com.sparta.devquiz.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
