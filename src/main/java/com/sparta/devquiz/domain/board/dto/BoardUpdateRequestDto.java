// BoardUpdateRequestDto.java
package com.sparta.devquiz.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String title;
    private String content;
}
