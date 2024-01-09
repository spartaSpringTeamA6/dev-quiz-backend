package com.sparta.devquiz.domain.board.dto;

import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.response.BoardResponseCode;
import com.sparta.devquiz.global.response.CommonResponseDto;
import lombok.Getter;

@Getter
public class BoardSingleGetResponseDto extends CommonResponseDto<Board> {
    private Long boardId;
    private String title;
    private String content;
    private Long userId;
    private Long quizId;

    public BoardSingleGetResponseDto(Board board) {
        super(BoardResponseCode.OK_GET_BOARD_INFO.getHttpStatus(),
                BoardResponseCode.OK_GET_BOARD_INFO.getMessage(),
                board);
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userId = board.getUser().getId();
        this.quizId = board.getQuiz().getId();
    }
}