package com.sparta.devquiz.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class BoardListGetResponseDto {
    private List<BoardSingleGetResponseDto> boards;
}
