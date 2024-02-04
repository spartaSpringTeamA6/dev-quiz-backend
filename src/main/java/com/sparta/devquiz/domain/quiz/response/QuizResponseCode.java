package com.sparta.devquiz.domain.quiz.response;


import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QuizResponseCode implements ResponseCode {

    // OK 200
    OK_GET_QUIZ(HttpStatus.OK, "GET QUIZ"),
    OK_GET_MY_SCORE(HttpStatus.OK, "GET MY SCORE"),
    OK_GET_RANDOM_QUIZZES(HttpStatus.OK, "GET 10 QUIZZES"),
    OK_SUBMIT_QUIZ_ANSWER(HttpStatus.OK, "SUBMIT QUIZ ANSWER"),
    OK_UPDATE_QUIZ(HttpStatus.OK, "SUCCESS TO UPDATE QUIZ"),
    OK_DELETE_QUIZ(HttpStatus.OK, "SUCCESS TO DELETE QUIZ"),
    OK_GET_CORRECT_QUIZZES(HttpStatus.OK, "GET CORRECT QUIZZES"),
    OK_GET_FAIL_QUIZZES(HttpStatus.OK, "GET FAIL QUIZZES"),
    OK_GET_PASS_QUIZZES(HttpStatus.OK, "GET PASS QUIZZES"),
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
