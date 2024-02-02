package com.sparta.devquiz.global.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtExceptionCode {

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-001", "Expired JWT token, 만료된 JWT token 입니다."),
    TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "JWT-002", "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-003", "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다."),
    ILLEGAL_TOKEN(HttpStatus.UNAUTHORIZED, "JWT-004", "JWT claims is empty, 잘못된 JWT 토큰 입니다."),

    BAD_REQUEST_TOKEN(HttpStatus.BAD_REQUEST, "JWT-005", "잘못된 JWT 토큰 요청입니다 .");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}