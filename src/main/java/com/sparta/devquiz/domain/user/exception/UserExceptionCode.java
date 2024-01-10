package com.sparta.devquiz.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionCode {

    // BAD REQUEST 400

    // NOT FOUND 404
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER-001", "해당 사용자를 찾을 수 없습니다.");

    // FORBIDDEN 403

    // CONFLICT 409

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}