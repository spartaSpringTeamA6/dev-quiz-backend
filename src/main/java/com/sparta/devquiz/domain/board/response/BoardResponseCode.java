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
    OK_UPDATE_BOARD(HttpStatus.OK, "SUCCESS TO UPDATE BOARD"),
    OK_DELETE_BOARD(HttpStatus.OK, "SUCCESS TO DELETE BOARD"),

    // CREATED 201
    CREATED_BOARD(HttpStatus.CREATED, "SUCCESS TO CREATE BOARD");

    private final HttpStatus httpStatus;
    private final String message;

    public HttpStatus getHttpStatus(BoardResponseCode responseCode){
        return responseCode.getHttpStatus();
    }

    public String getMessage(BoardResponseCode responseCode){
        return responseCode.getMessage();
    }

}
