package com.sparta.devquiz.domain.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardExceptionCode {

    // BAD REQUEST 400

    // NOT FOUND 404
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "BOARD-001", "해당 보드를 찾을 수 없습니다."),
    NOT_FOUND_QUIZ(HttpStatus.NOT_FOUND, "BOARD-002", "해당 퀴즈를 찾을 수 없습니다."),
    ALREADY_DELETED_BOARD(HttpStatus.NOT_FOUND, "BOARD-004", "이미 삭제된 보드입니다."),
    // FORBIDDEN 403
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "BOARD-003", "권한이 없습니다.");

    // CONFLICT 409

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
