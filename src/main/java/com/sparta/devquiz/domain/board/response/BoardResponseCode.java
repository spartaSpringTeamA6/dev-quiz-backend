package com.sparta.devquiz.domain.board.response;

import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardResponseCode implements ResponseCode {

    // OK 200
    OK_GET_BOARDLIST_INFO(HttpStatus.OK.value(), "SUCCESS TO GET BOARDLIST"),
    OK_GET_BOARD_INFO(HttpStatus.OK.value(), "SUCCESS TO GET BOARD"),

    // CREATED 201
    CREATED_BOARD(HttpStatus.CREATED.value(), "SUCCESS TO CREATE BOARD"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서 DATA가 "" 인 것들을 204로 사용
    OK_UPDATE_BOARD(HttpStatus.NO_CONTENT.value(), "SUCCESS TO UPDATE BOARD"),
    OK_DELETE_BOARD(HttpStatus.NO_CONTENT.value(), "SUCCESS TO DELETE BOARD");

    private final Integer httpStatus;
    private final String message;

}
