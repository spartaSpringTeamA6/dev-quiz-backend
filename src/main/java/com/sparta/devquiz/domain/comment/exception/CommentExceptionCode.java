package com.sparta.devquiz.domain.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentExceptionCode {

    // BAD REQUEST 400

    // NOT FOUND 404
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "COMMENT-001", "해당 보드를 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND,"COMMENT-002", "해당 댓글을 찾을 수 없습니다." ),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "COMMENT-003", "권한이 없습니다.");
    // FORBIDDEN 403


    // CONFLICT 409

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
