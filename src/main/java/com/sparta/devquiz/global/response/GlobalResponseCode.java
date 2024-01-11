package com.sparta.devquiz.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalResponseCode implements ResponseCode {

    // OK 200
    OK(HttpStatus.OK, "REQUEST SUCCESS, OK"),

    // RANKING API
    OK_GET_ALL_RANKING(HttpStatus.OK, "GET ALL RANKING"),
    OK_GET_ALL_GROUP_RANKING(HttpStatus.OK, "GET TEAM RANKING"),

    // CREATED 201
    CREATE(HttpStatus.CREATED, "REQUEST SUCCESS, CREATE"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서 DATA가 "" 인 것들을 204로 사용
    NO_CONTENT(HttpStatus.NO_CONTENT, "REQUEST SUCCESS, NO CONTENT");

    private final HttpStatus httpStatus;
    private final String message;

    public HttpStatus getHttpStatus(GlobalResponseCode responseCode){
        return responseCode.getHttpStatus();
    }

    public String getMessage(GlobalResponseCode responseCode){
        return responseCode.getMessage();
    }
}
