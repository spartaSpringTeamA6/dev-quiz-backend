package com.sparta.devquiz.domain.point.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PointResponseCode {

    // OK 200
    OK_GET_MY_POINT(HttpStatus.OK.value(), "GET MY POINT"),

    // CREATED 201
    CREATED_SAVE_POINT(HttpStatus.CREATED.value(), "SUCCESS TO SAVE POINT"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서  DATA가 "" 인 것들을 204로 사용
    NO_CONTENT_USE_POINT(HttpStatus.NO_CONTENT.value(), "SUCCESS TO USE MY POINT");

    private final Integer httpStatus;
    private final String message;

}
