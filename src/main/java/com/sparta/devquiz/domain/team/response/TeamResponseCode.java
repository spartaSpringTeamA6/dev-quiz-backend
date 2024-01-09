package com.sparta.devquiz.domain.team.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TeamResponseCode {

    // OK 200
    OK_GET_TEAM_INFO(HttpStatus.OK.value(), "GET TEAM INFORMATION"),
    OK_INVITE_TEAM(HttpStatus.OK.value(), "SUCCESS TO INVITE TEAM"),
    OK_GET_RANKING_INFO_IN_TEAM(HttpStatus.OK.value(),
            "SUCCESS TO GET RANKING INFORMATION IN TEAM"),
    OK_GET_MY_RANKING_IN_TEAM(HttpStatus.OK.value(),
            "SUCCESS TO GET MY RANKING INFORMATION IN TEAM"),

    // CREATED 201
    CREATED_TEAM(HttpStatus.CREATED.value(), "SUCCESS TO CREATE TEAM"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서  DATA가 "" 인 것들을 204로 사용
    NO_CONTENT_UPDATE_TEAM_NAME(HttpStatus.NO_CONTENT.value(), "SUCCESS TO UPDATE TEAM NAME"),
    NO_CONTENT_CHANGE_TEAM_ADMIN(HttpStatus.NO_CONTENT.value(), "SUCCESS TO CHANGE TEAM ADMIN"),
    NO_CONTENT_EXPEL_TEAM_MEMBER(HttpStatus.NO_CONTENT.value(), "SUCCESS TO EXPEL TEAM MEMBER"),
    NO_CONTENT_DELETE_TEAM(HttpStatus.NO_CONTENT.value(), "SUCCESS TO DELETE TEAM");

    private final Integer httpStatus;
    private final String message;

}
