package com.sparta.devquiz.domain.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardResponseCode {

    // OK 200
    OK_GET_BOARDLIST_INFO(HttpStatus.OK.value(), "SUCCESS TO GET BOARDLIST"),
    OK_GET_BOARD_INFO(HttpStatus.OK.value(), "SUCCESS TO GET BOARD"),

    // CREATED 201
    CREATED_BOARD(HttpStatus.CREATED.value(), "SUCCESS TO CREATE BOARD"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서 DATA가 "" 인 것들을 204로 사용
    NO_CONTENT_UPDATE_BOARD(HttpStatus.NO_CONTENT.value(), "SUCCESS TO UPDATE BOARD"),
    NO_CONTENT_DELETE_BOARD(HttpStatus.NO_CONTENT.value(), "SUCCESS TO DELETE BOARD");

    private final Integer httpStatus;
    private final String message;

}
