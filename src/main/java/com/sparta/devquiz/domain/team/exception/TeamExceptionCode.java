package com.sparta.devquiz.domain.team.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TeamExceptionCode {

    // BAD REQUEST 400

    // NOT FOUND 404
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND, "TEAM-001", "해당 팀을 찾을 수 없습니다.");

    // FORBIDDEN 403

    // CONFLICT 409

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}