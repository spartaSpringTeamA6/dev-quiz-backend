package com.sparta.devquiz.domain.board.dto.responseDto;

import com.sparta.devquiz.domain.board.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "보드 정보 응답 Dto")
public class BoardResponse {

    @Schema(description = "Board id", defaultValue = "1")
    private Long boardId;

    @Schema(description = "Board title", defaultValue = "DevQuiz")
    private String title;

    @Schema(description = "Board content", defaultValue = "DevQuizContent")
    private String content;

    public static BoardResponse of(Board board) {
        return BoardResponse
                .builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }

    public static List<BoardResponse> of(List<Board> boardList) {
        return boardList
                .stream()
                .map(BoardResponse::of)
                .toList();
    }
}
