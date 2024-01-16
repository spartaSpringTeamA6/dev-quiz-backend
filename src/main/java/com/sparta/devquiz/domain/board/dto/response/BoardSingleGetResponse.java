package com.sparta.devquiz.domain.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardSingleGetResponse {

    private Long id;
    private String title;
    private String content;

}

