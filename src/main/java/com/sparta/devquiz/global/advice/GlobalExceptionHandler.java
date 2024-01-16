package com.sparta.devquiz.global.advice;

import java.util.HashMap;
import java.util.Map;

import com.sparta.devquiz.global.exception.CustomException;
import com.sparta.devquiz.global.exception.GlobalExceptionCode;
import com.sparta.devquiz.global.response.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException exception) {
        log.error("RuntimeException: ", exception);
        return ResponseEntity.status(GlobalExceptionCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(CommonResponseDto.of(GlobalExceptionCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException exception) {
        log.error("HttpMessageNotReadableException: ", exception);
        return ResponseEntity.status(GlobalExceptionCode.INVALID_PARAMETER.getHttpStatus())
                .body(CommonResponseDto.of(GlobalExceptionCode.INVALID_PARAMETER));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> IllegalArgumentHandler(IllegalArgumentException exception) {
        log.error("IllegalArgumentException: ", exception);
        return ResponseEntity.status(GlobalExceptionCode.INVALID_VALUE.getHttpStatus())
                .body(CommonResponseDto.of(GlobalExceptionCode.INVALID_VALUE));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidHandler(
            MethodArgumentNotValidException exception) {
        log.error("handleMethodArgumentNotValidException: ", exception);
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(
                error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));

        return ResponseEntity.status(GlobalExceptionCode.INVALID_VALUE.getHttpStatus())
                .body(CommonResponseDto.of(GlobalExceptionCode.INVALID_VALUE));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(CustomException exception) {
        log.error("CustomException: ", exception);
        return ResponseEntity.status(exception.getHttpStatus())
                .body(CommonResponseDto.of(exception));
    }

}

