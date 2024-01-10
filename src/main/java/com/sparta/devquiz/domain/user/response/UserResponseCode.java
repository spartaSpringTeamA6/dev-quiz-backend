package com.sparta.devquiz.domain.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserResponseCode {

    // OK 200
    OK(HttpStatus.OK, "REQUEST SUCCESS"),
    LOGIN(HttpStatus.OK, "LOGIN SUCCESS"),
    LOGOUT(HttpStatus.OK, "LOGOUT SUCCESS"),
    GET_MY_INFO(HttpStatus.OK, "SUCCESS TO GET MY INFORMATION"),
    DELETE_USER(HttpStatus.OK, "SUCCESS TO DELETE USER"),

    GET_MY_BOARD_INFO(HttpStatus.OK, "SUCCESS TO GET MY BOARD"),
    GET_MY_COMMENT(HttpStatus.OK, "SUCCESS TO GET MY COMMENT"),
    GET_MY_QUIZ(HttpStatus.OK, "SUCCESS TO GET MY QUIZ"),
    GET_MY_CORRECT_QUIZ(HttpStatus.OK, "SUCCESS TO GET MY CORRECT QUIZ"),
    GET_MY_FAILED_QUIZ(HttpStatus.OK, "SUCCESS TO GET MY FAILED QUIZ"),
    GET_MY_PASSED_QUIZ(HttpStatus.OK, "SUCCESS TO GET MY PASSED QUIZ"),
    GET_MY_GROUP(HttpStatus.OK, "SUCCESS TO GET MY GROUP"),
    ACCEPT_GROUP_INVITATION(HttpStatus.OK, "ACCEPT INVITATION"),
    REJECT_GROUP_INVITATION(HttpStatus.OK, "REJECT INVITATION"),
    GET_MY_RANKING(HttpStatus.OK, "GET MY RANKING"),

    // CREATED 201
    SIGNUP(HttpStatus.CREATED, "SIGN UP"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서  DATA가 "" 인 것들을 204로 사용
    UPDATE_MY_INFO(HttpStatus.NO_CONTENT, "SUCCESS TO UPDATE MY INFORMATION");

    private final HttpStatus httpStatus;
    private final String message;

}
