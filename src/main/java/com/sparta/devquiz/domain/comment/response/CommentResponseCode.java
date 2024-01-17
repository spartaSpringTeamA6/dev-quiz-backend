package com.sparta.devquiz.domain.comment.response;

import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentResponseCode implements ResponseCode {

    // OK 200
    OK_GET_ALL_COMMENT(HttpStatus.OK, "GET ALL COMMENTS"),
    OK_UPDATE_COMMENT(HttpStatus.OK, "SUCCESS TO UPDATE COMMENT"),
    OK_DELETE_COMMENT(HttpStatus.OK, "SUCCESS TO DELETE COMMENT"),
    OK_DELETE_LIKE(HttpStatus.OK, "SUCCESS TO DELETE LIKE"),

    // CREATED 201
    CREATED_COMMENT(HttpStatus.CREATED, "SUCCESS TO CREATE COMMENT"),
    CREATED_LIKE(HttpStatus.CREATED, "SUCCESS TO LIKE COMMENT");

    private final HttpStatus httpStatus;
    private final String message;

}
