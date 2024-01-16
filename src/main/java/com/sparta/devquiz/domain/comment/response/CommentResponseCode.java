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

    // CREATED 201
    CREATED_COMMENT(HttpStatus.CREATED, "SUCCESS TO CREATE COMMENT"),
    CREATED_LIKE(HttpStatus.CREATED, "SUCCESS TO LIKE COMMENT"),

    // OK 204:  기존 RESPONSE CODE 200에서  DATA가 "" 인 것들을 204로 사용
    OK_UPDATE_COMMENT(HttpStatus.NO_CONTENT, "SUCCESS TO UPDATE COMMENT"),
    OK_DELETE_COMMENT(HttpStatus.NO_CONTENT, "SUCCESS TO DELETE COMMENT"),
    OK_DELETE_LIKE(HttpStatus.NO_CONTENT, "SUCCESS TO DELETE LIKE");

    private final HttpStatus httpStatus;
    private final String message;

}
