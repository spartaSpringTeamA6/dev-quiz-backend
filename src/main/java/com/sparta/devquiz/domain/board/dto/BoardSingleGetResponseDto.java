package com.sparta.devquiz.domain.board.dto;

import com.sparta.devquiz.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardSingleGetResponseDto {

    private String statusCode;
    private String message;
    private Board data;

    public BoardSingleGetResponseDto(String statusCode, String message, Board data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}