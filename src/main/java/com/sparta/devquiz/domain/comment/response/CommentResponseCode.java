package com.sparta.devquiz.domain.comment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentResponseCode {

    // OK 200
    OK_GET_ALL_COMMENT(HttpStatus.OK.value(), "GET ALL COMMENTS"),

    // CREATED 201
    CREATED_COMMENT(HttpStatus.CREATED.value(), "SUCCESS TO CREATE COMMENT"),
    CREATED_LIKE(HttpStatus.CREATED.value(), "SUCCESS TO LIKE COMMENT"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서  DATA가 "" 인 것들을 204로 사용
    NO_CONTENT_UPDATE_COMMENT(HttpStatus.NO_CONTENT.value(), "SUCCESS TO UPDATE COMMENT"),
    NO_CONTENT_DELETE_COMMENT(HttpStatus.NO_CONTENT.value(), "SUCCESS TO DELETE COMMENT"),
    NO_CONTENT_DELETE_LIKE(HttpStatus.NO_CONTENT.value(), "SUCCESS TO DELETE LIKE");

    private final Integer httpStatus;
    private final String message;

}
