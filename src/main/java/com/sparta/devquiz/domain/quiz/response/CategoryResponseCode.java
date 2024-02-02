package com.sparta.devquiz.domain.quiz.response;

import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CategoryResponseCode implements ResponseCode {

    // OK 200
    OK_GET_CATEGORY(HttpStatus.OK, "GET CATEGORY"),
    OK_GET_CUSTOM_CATEGORY(HttpStatus.OK, "GET MY CATEGORY"),
    OK_GET_TOTAL_QUIZ_COUNT(HttpStatus.OK, "GET TOTAL QUIZ COUNT"),

    // CREATED 201
    OK_CREATE_CATEGORY(HttpStatus.OK, "SUCCESS TO CREATE CATEGORY");

    private final HttpStatus httpStatus;
    private final String message;

    public HttpStatus getHttpStatus(CategoryResponseCode responseCode) {
        return responseCode.getHttpStatus();
    }

    public String getMessage(CategoryResponseCode responseCode) {
        return responseCode.getMessage();
    }

}
