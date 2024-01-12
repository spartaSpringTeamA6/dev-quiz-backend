package com.sparta.devquiz.domain.comment.exception;

import com.sparta.devquiz.global.exception.CustomException;
import lombok.Getter;

@Getter
public class CommentCustomException extends CustomException {

    public CommentCustomException(CommentExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(),
                exceptionCode.getMessage());
    }
}
