package com.sparta.devquiz.domain.quiz.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QuizResponseCode {

    // OK 200
    OK_GET_QUIZ(HttpStatus.OK.value(), "GET QUIZ"),
    OK_GET_MY_SCORE(HttpStatus.OK.value(), "GET MY SCORE"),
    OK_GET_RANDOM_QUIZZES(HttpStatus.OK.value(), "GET 10 QUIZZES"),
    OK_SUBMIT_QUIZ_ANSWER(HttpStatus.OK.value(), "SUBMIT QUIZ ANSWER"),

    // CREATED 201
    CREATED_QUIZ(HttpStatus.CREATED.value(), "SUCCESS TO CREATE QUIZ"),

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서  DATA가 "" 인 것들을 204로 사용
    NO_CONTENT_UPDATE_QUIZ(HttpStatus.NO_CONTENT.value(), "SUCCESS TO UPDATE QUIZ"),
    NO_CONTENT_DELETE_QUIZ(HttpStatus.NO_CONTENT.value(), "SUCCESS TO DELETE QUIZ");

    private final Integer httpStatus;
    private final String message;

}
