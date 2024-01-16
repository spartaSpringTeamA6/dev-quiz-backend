package com.sparta.devquiz.domain.board.response;

import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardResponseCode implements ResponseCode {

    // OK 200
    OK_GET_BOARDLIST_INFO(HttpStatus.OK, "SUCCESS TO GET BOARDLIST"),
    OK_GET_BOARD_INFO(HttpStatus.OK, "SUCCESS TO GET BOARD"),

    // CREATED 201
    CREATED_BOARD(HttpStatus.CREATED, "SUCCESS TO CREATE BOARD"),

    // OK 204:  기존 RESPONSE CODE 200에서 DATA가 "" 인 것들을 204로 사용
    OK_UPDATE_BOARD(HttpStatus.NO_CONTENT, "SUCCESS TO UPDATE BOARD"),
    OK_DELETE_BOARD(HttpStatus.NO_CONTENT, "SUCCESS TO DELETE BOARD");

    private final HttpStatus httpStatus;
    private final String message;

    public HttpStatus getHttpStatus(BoardResponseCode responseCode){
        return responseCode.getHttpStatus();
    }

    public String getMessage(BoardResponseCode responseCode){
        return responseCode.getMessage();
    }

}
