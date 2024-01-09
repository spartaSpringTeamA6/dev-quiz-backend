package com.sparta.devquiz.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalResponseCode {

    // OK 200
    OK(HttpStatus.OK.value(), "REQUEST SUCCESS, OK"),

    // RANKING API
    OK_GET_ALL_RANKING(HttpStatus.OK.value(), "GET ALL RANKING"),
    OK_GET_ALL_GROUP_RANKING(HttpStatus.OK.value(), "GET TEAM RANKING"),

    // CREATED 201
    CREATE(HttpStatus.CREATED.value(), "REQUEST SUCCESS, CREATE"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서 DATA가 "" 인 것들을 204로 사용
    NO_CONTENT(HttpStatus.NO_CONTENT.value(), "REQUEST SUCCESS, NO CONTENT");

    private final Integer httpStatus;
    private final String message;

}
