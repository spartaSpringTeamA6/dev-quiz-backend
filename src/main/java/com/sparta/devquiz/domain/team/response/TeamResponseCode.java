package com.sparta.devquiz.domain.team.response;

import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TeamResponseCode implements ResponseCode {

    // OK 200
    OK_GET_TEAM_INFO(HttpStatus.OK, "GET TEAM INFORMATION"),
    OK_INVITE_TEAM(HttpStatus.OK, "SUCCESS TO INVITE TEAM"),
    OK_GET_USER_RANKING_IN_TEAM(HttpStatus.OK,
            "SUCCESS TO GET USER RANKING INFORMATION IN TEAM"),
    OK_GET_TEAM_RANKING_IN_ALL(HttpStatus.OK,
            "SUCCESS TO GET TEAM RANKING INFORMATION IN ALL"),
    OK_UPDATE_TEAM_NAME(HttpStatus.NO_CONTENT, "SUCCESS TO UPDATE TEAM NAME"),
    OK_CHANGE_TEAM_ADMIN(HttpStatus.NO_CONTENT, "SUCCESS TO CHANGE TEAM ADMIN"),
    OK_EXPEL_TEAM_MEMBER(HttpStatus.NO_CONTENT, "SUCCESS TO EXPEL TEAM MEMBER"),
    OK_WITHDRAW_TEAM(HttpStatus.NO_CONTENT, "SUCCESS TO EXPEL TEAM MEMBER"),
    OK_DELETE_TEAM(HttpStatus.NO_CONTENT, "SUCCESS TO DELETE TEAM"),
    OK_INVITE_TEAM_USER(HttpStatus.NO_CONTENT, "SUCCESS TO INVITE TEAM USER"),

    // CREATED 201
    CREATED_TEAM(HttpStatus.CREATED, "SUCCESS TO CREATE TEAM");

    // ACCEPTED 202

    // NO CONTENT 204

    private final HttpStatus httpStatus;
    private final String message;

    public HttpStatus getHttpStatus(TeamResponseCode responseCode){
        return responseCode.getHttpStatus();
    }

    public String getMessage(TeamResponseCode responseCode){
        return responseCode.getMessage();
    }

}
