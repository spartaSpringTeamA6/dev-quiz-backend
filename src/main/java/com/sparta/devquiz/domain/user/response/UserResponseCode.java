package com.sparta.devquiz.domain.user.response;

import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserResponseCode implements ResponseCode {

    // OK 200
    OK(HttpStatus.OK, "REQUEST SUCCESS"),
    LOGIN(HttpStatus.OK, "LOGIN SUCCESS"),
    LOGOUT(HttpStatus.OK, "LOGOUT SUCCESS"),
    GET_MY_INFO(HttpStatus.OK, "SUCCESS TO GET MY INFORMATION"),
    DELETE_USER(HttpStatus.OK, "SUCCESS TO DELETE USER"),

    GET_MY_BOARD(HttpStatus.OK, "SUCCESS TO GET MY BOARDS"),
    GET_MY_COMMENT(HttpStatus.OK, "SUCCESS TO GET MY COMMENTS"),
    GET_MY_QUIZ(HttpStatus.OK, "SUCCESS TO GET MY QUIZZES"),
    GET_MY_CORRECT_QUIZ(HttpStatus.OK, "SUCCESS TO GET MY CORRECT QUIZZES"),
    GET_MY_FAILED_QUIZ(HttpStatus.OK, "SUCCESS TO GET MY FAILED QUIZZES"),
    GET_MY_PASSED_QUIZ(HttpStatus.OK, "SUCCESS TO GET MY PASSED QUIZZES"),
    GET_MY_TEAM(HttpStatus.OK, "SUCCESS TO GET MY TEAMS"),
    GET_TEAM_INVITATION(HttpStatus.OK, "ACCEPT INVITATION"),
    ACCEPT_TEAM_INVITATION(HttpStatus.OK, "ACCEPT INVITATION"),
    REJECT_TEAM_INVITATION(HttpStatus.OK, "REJECT INVITATION"),
    GET_MY_RANKING(HttpStatus.OK, "GET MY RANKING"),

    UPDATE_MY_INFO(HttpStatus.OK, "SUCCESS TO UPDATE MY INFORMATION"),

    // CREATED 201
    SIGNUP(HttpStatus.CREATED, "SUCCESS TO SIGN UP"),
    REISSUE(HttpStatus.CREATED, "SUCCESS TO REISSUE");

    private final HttpStatus httpStatus;
    private final String message;

    public HttpStatus getHttpStatus(UserResponseCode responseCode) {
        return responseCode.getHttpStatus();
    }

    public String getMessage(UserResponseCode responseCode){
        return responseCode.getMessage();
    }
}
