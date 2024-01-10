package com.sparta.devquiz.domain.coin.exception;

import com.sparta.devquiz.global.exception.CustomException;
import lombok.Getter;

@Getter
public class CoinCustomException extends CustomException {

    public CoinCustomException(CoinExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(),
                exceptionCode.getMessage());
    }
}
