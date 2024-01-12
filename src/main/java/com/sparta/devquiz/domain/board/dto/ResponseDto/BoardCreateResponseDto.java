package com.sparta.devquiz.domain.board.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardCreateResponseDto {
    private Long boardId;
    private String title;
    private String content;
}