package com.sparta.devquiz.domain.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentExceptionCode {

    // BAD REQUEST 400
    ALREADY_LIKED(HttpStatus.BAD_REQUEST, "COMMENT-004", "이미 좋아요를 누른 댓글입니다."),
    NOT_LIKED(HttpStatus.BAD_REQUEST, "COMMENT-005", "좋아요를 누르지 않은 댓글입니다."),

    // NOT FOUND 404
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "COMMENT-001", "해당 보드를 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "COMMENT-002", "해당 댓글을 찾을 수 없습니다."),
    ALREADY_DELETED_COMMENT(HttpStatus.NOT_FOUND, "BOARD-006", "이미 삭제된 댓글입니다."),
    // FORBIDDEN 403
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "COMMENT-003", "권한이 없습니다.");

    // CONFLICT 409

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
