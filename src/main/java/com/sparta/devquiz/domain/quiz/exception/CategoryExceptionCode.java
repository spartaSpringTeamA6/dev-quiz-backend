package com.sparta.devquiz.domain.quiz.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CategoryExceptionCode {
    // BAD REQUEST 400
    INVALID_CATEGORY_TITLE(HttpStatus.BAD_REQUEST, "CATEGORY-002", "카테고리 이름이 유효하지 않습니다."),
    // NOT FOUND 404
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "CATEGORY-001", "해당 카테고리를 찾을 수 없습니다.");

    // FORBIDDEN 403

    // CONFLICT 409

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
