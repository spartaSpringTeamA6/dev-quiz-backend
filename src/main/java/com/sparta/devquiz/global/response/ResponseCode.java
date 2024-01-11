package com.sparta.devquiz.global.response;
import org.springframework.http.HttpStatus;

public interface ResponseCode {

    public HttpStatus getHttpStatus();
    public String getMessage();

}