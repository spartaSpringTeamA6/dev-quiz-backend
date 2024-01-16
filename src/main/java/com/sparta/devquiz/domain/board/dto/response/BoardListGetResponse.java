package com.sparta.devquiz.domain.board.dto.response;

import com.sparta.devquiz.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class BoardListGetResponse {
    private List<BoardSingleGetResponse> boards;

    public static BoardGetResponse of(List<Board> boards) {
        List<BoardListGetResponse> boardListGetResponseDtos = boards
                .stream()
                .map(BoardG)
    }
}
