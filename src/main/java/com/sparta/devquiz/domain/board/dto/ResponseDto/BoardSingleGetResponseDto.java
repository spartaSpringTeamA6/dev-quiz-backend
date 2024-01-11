package com.sparta.devquiz.domain.board.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardSingleGetResponseDto {

    private Long id;
    private String title;
    private String content;

}

