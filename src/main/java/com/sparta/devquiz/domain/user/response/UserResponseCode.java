package com.sparta.devquiz.domain.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserResponseCode {

    // OK 200
    OK(HttpStatus.OK.value(), "REQUEST SUCCESS"),
    LOGIN(HttpStatus.OK.value(), "LOGIN SUCCESS"),
    LOGOUT(HttpStatus.OK.value(), "LOGOUT SUCCESS"),
    GET_MY_INFO(HttpStatus.OK.value(), "SUCCESS TO GET MY INFORMATION"),
    DELETE_USER(HttpStatus.OK.value(), "SUCCESS TO DELETE USER"),

    GET_MY_BOARD_INFO(HttpStatus.OK.value(), "SUCCESS TO GET MY BOARD"),
    GET_MY_COMMENT(HttpStatus.OK.value(), "SUCCESS TO GET MY COMMENT"),
    GET_MY_QUIZ(HttpStatus.OK.value(), "SUCCESS TO GET MY QUIZ"),
    GET_MY_CORRECT_QUIZ(HttpStatus.OK.value(), "SUCCESS TO GET MY CORRECT QUIZ"),
    GET_MY_FAILED_QUIZ(HttpStatus.OK.value(), "SUCCESS TO GET MY FAILED QUIZ"),
    GET_MY_PASSED_QUIZ(HttpStatus.OK.value(), "SUCCESS TO GET MY PASSED QUIZ"),
    GET_MY_GROUP(HttpStatus.OK.value(), "SUCCESS TO GET MY GROUP"),
    ACCEPT_GROUP_INVITATION(HttpStatus.OK.value(), "ACCEPT INVITATION"),
    REJECT_GROUP_INVITATION(HttpStatus.OK.value(), "REJECT INVITATION"),
    GET_MY_RANKING(HttpStatus.OK.value(), "GET MY RANKING"),

    // CREATED 201
    SIGNUP(HttpStatus.CREATED.value(), "SIGN UP"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서  DATA가 "" 인 것들을 204로 사용
    UPDATE_MY_INFO(HttpStatus.NO_CONTENT.value(), "SUCCESS TO UPDATE MY INFORMATION");

    private final Integer httpStatus;
    private final String message;

}
