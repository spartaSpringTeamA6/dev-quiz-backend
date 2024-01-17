package com.sparta.devquiz.domain.quiz.response;


import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QuizResponseCode implements ResponseCode {

    // OK 200
    OK_GET_QUIZ(HttpStatus.OK.value(), "GET QUIZ"),
    OK_GET_MY_SCORE(HttpStatus.OK.value(), "GET MY SCORE"),
    OK_GET_RANDOM_QUIZZES(HttpStatus.OK.value(), "GET 10 QUIZZES"),
    OK_SUBMIT_QUIZ_ANSWER(HttpStatus.OK.value(), "SUBMIT QUIZ ANSWER"),

    // CREATED 201
    CREATED_QUIZ(HttpStatus.CREATED, "SUCCESS TO CREATE QUIZ");

    // NO CONTENT 204:  기존 RESPONSE CODE 200에서  DATA가 "" 인 것들을 204로 사용

    private final HttpStatus httpStatus;
    private final String message;
    public HttpStatus getHttpStatus(QuizResponseCode responseCode){
        return responseCode.getHttpStatus();
    }

    public String getMessage(QuizResponseCode responseCode){
        return responseCode.getMessage();
    }

}
