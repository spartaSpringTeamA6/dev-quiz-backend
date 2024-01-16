package com.sparta.devquiz.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentListGetResponse {
    private List<CommentSingleGetResponse> comments;
}
