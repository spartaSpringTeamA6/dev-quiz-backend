package com.sparta.devquiz.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponseDto<T> {

    private Integer status;
    private String message;
    private T data;

    public static <T> CommonResponseDto<T> of(Integer status, String message, T data) {
        return new CommonResponseDto<T>(status, message, data);
    }

    public static <T> CommonResponseDto<T> of(GlobalResponseCode responseCode, T data) {
        return new CommonResponseDto<>(
                responseCode.getHttpStatus(),
                responseCode.getMessage(),
                data
        );
    }

}
