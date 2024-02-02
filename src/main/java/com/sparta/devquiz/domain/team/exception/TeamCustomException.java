package com.sparta.devquiz.domain.team.exception;

import com.sparta.devquiz.global.exception.CustomException;
import lombok.Getter;

@Getter
public class TeamCustomException extends CustomException {

    public TeamCustomException(TeamExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(),
                exceptionCode.getMessage());
    }

}
