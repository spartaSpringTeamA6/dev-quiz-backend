package com.sparta.devquiz.domain.coin.response;

import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CoinResponseCode implements ResponseCode {

    // OK 200
    OK_GET_MY_COIN(HttpStatus.OK, "GET MY COIN"),

    // CREATED 201
    CREATED_SAVE_COIN(HttpStatus.CREATED, "SUCCESS TO SAVE COIN"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서  DATA가 "" 인 것들을 204로 사용
    NO_CONTENT_USE_COIN(HttpStatus.NO_CONTENT, "SUCCESS TO USE MY COIN");

    private final HttpStatus httpStatus;
    private final String message;

    public HttpStatus getHttpStatus(CoinResponseCode responseCode) {
        return responseCode.getHttpStatus();
    }

    public String getMessage(CoinResponseCode responseCode) {
        return responseCode.getMessage();
    }

}
