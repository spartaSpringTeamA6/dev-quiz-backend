package com.sparta.devquiz.domain.team.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TeamExceptionCode {

    // BAD REQUEST 400
    BAD_REQUEST_INVALID_REQUEST_TEAM_NAME(HttpStatus.BAD_REQUEST, "TEAM-007", "유효하지 않은 팀 이름입니다."),
    BAD_REQUEST_INVALID_REQUEST_USERNAME(HttpStatus.BAD_REQUEST, "TEAM-008", "유효하지 않은 유저 닉네임입니다."),
    BAD_REQUEST_INVALID_REQUEST_DELETE_ADMIN(HttpStatus.BAD_REQUEST, "TEAM-009", "관리자는 삭제할 수 없습니다."),

    // NOT FOUND 404
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND, "TEAM-001", "해당 팀을 찾을 수 없습니다."),
    NOT_FOUND_TEAM_ADMIN(HttpStatus.NOT_FOUND, "TEAM-004", "해당 팀의 관리자를 찾을 수 없습니다."),
    NOT_FOUND_TEAM_USER(HttpStatus.NOT_FOUND, "TEAM-006", "해당 팀의 해당 유저를 찾을 수 없습니다."),

    // FORBIDDEN 403
    FORBIDDEN_TEAM_USER(HttpStatus.FORBIDDEN, "TEAM-002", "해당 유저는 팀 유저 권한이 없습니다."),
    FORBIDDEN_TEAM_ADMIN(HttpStatus.FORBIDDEN, "TEAM-003", "해당 유저는 팀 관리자 권한이 없습니다."),

    // CONFLICT 409
    CONFLICT_TEAM_NAME_IN_USE(HttpStatus.CONFLICT, "TEAM-005", "해당 팀 이름이 이미 사용 중 입니다");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}