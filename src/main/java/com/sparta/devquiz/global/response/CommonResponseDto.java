package com.sparta.devquiz.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "공통 응답 dto")
public class CommonResponseDto<T> {

    private int status;
    private String message;
    private T data;

    public static <T> CommonResponseDto<T> of(Integer status, String message, T data) {
        return new CommonResponseDto<T>(status, message, data);
    }

    public static <T> CommonResponseDto<T> of(ResponseCode responseCode, T data) {
        return new CommonResponseDto<T>(
                responseCode.getHttpStatus().value(),
                responseCode.getMessage(),
                data
        );
    }

}
