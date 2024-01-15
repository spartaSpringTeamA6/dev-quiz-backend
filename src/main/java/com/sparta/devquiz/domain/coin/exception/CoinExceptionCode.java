package com.sparta.devquiz.domain.coin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CoinExceptionCode {

    // BAD REQUEST 400
    BAD_REQUEST_COIN(HttpStatus.BAD_REQUEST,"COIN-001","잘못된 입력입니다."),
    BAD_REQUEST_NOT_ENOUGH_COIN(HttpStatus.BAD_REQUEST,"COIN-002","포인트가 부족합니다."),

    // FORBIDDEN 403


    // NOT FOUND 404
    NOT_FOUND_COIN_USER(HttpStatus.NOT_FOUND,"COIN-004","유저를 찾을 수 없습니다.");

    // CONFLICT 409

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
