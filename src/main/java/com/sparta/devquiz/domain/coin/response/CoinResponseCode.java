package com.sparta.devquiz.domain.coin.response;

import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CoinResponseCode implements ResponseCode {

    // OK 200
    OK_GET_MY_COIN(HttpStatus.OK, "GET MY COIN"),
    OK_USE_COIN(HttpStatus.OK, "SUCCESS TO USE MY COIN"),

    // CREATED 201
    CREATED_SAVE_COIN(HttpStatus.CREATED, "SUCCESS TO SAVE COIN");

    private HttpStatus httpStatus;
    private String message;

    public HttpStatus getHttpStatus(CoinResponseCode responseCode) {
        return responseCode.getHttpStatus();
    }

    public String getMessage(CoinResponseCode responseCode) {
        return responseCode.getMessage();
    }

}
