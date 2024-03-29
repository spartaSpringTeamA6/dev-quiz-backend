package com.sparta.devquiz.domain.coin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CoinExceptionCode {

    // BAD REQUEST 400
    BAD_REQUEST_COIN(HttpStatus.BAD_REQUEST,"COIN-001","잘못된 입력입니다."),
    BAD_REQUEST_NOT_ENOUGH_COIN(HttpStatus.BAD_REQUEST,"COIN-002","포인트가 부족합니다."),
    BAD_REQUEST_INVALID_QUIZ_STATUS(HttpStatus.BAD_REQUEST,"COIN-003","잘못된 퀴즈 스테이터스입니다."),

    // FORBIDDEN 403


    // NOT FOUND 404
    NOT_FOUND_COIN_USER(HttpStatus.NOT_FOUND,"COIN-004","유저를 찾을 수 없습니다.");

    // CONFLICT 409

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
