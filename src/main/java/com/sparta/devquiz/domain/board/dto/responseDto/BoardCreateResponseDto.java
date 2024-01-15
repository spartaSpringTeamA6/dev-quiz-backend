package com.sparta.devquiz.domain.board.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardCreateResponseDto {
    private Long boardId;
    private String title;
    private String content;
}