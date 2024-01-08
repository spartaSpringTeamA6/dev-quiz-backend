package com.sparta.devquiz.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalExceptionCode {

    INVALID_VALUE(HttpStatus.BAD_REQUEST, "COMMON-001", "값이 유효하지 않습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON-002", "파라미터가 누락되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-003", "내부 서버 에러 입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
