package com.sparta.devquiz.domain.comment.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentListGetResponseDto {
    private List<CommentSingleGetResponseDto> comments;
}
