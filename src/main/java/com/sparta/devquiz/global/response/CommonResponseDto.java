package com.sparta.devquiz.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공통 응답 dto")
public class CommonResponseDto<T> {

    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public CommonResponseDto (int status, String message){
        this.status = status;
        this.message = message;
    }

    public static <T> CommonResponseDto<T> of(int status, String message, T data) {
        return new CommonResponseDto<T>(status,message,data);
    }

    public static <T> CommonResponseDto<T> of(ResponseCode responseCode, T data) {
        return new CommonResponseDto<T>(
                responseCode.getHttpStatus().value(),
                responseCode.getMessage(),
                data
        );
    }

    public static <T> CommonResponseDto<T> of(ResponseCode responseCode) {
        return new CommonResponseDto<T>(
                responseCode.getHttpStatus().value(),
                responseCode.getMessage()
        );
    }

}
