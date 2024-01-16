package com.sparta.devquiz.domain.board.dto.response;

import com.sparta.devquiz.domain.board.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "보드 정보 응답 Dto")
public class BoardDetailsResponse {

    @Column
    @Schema(description = "보드 ID", defaultValue = "1")
    private Long boardId;

    @Column
    @Schema(description = "보드 이름", defaultValue = "날아라 슈퍼보드")
    private String title;

    @Column
    @Schema(description = "보드 설명", defaultValue = "치키치키차카차카")
    private String content;

    public static BoardDetailsResponse of(Board board) {
        return BoardDetailsResponse
                .builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }

    public static List<BoardDetailsResponse> of(List<Board> boardList) {
        return boardList
                .stream()
                .map(BoardDetailsResponse::of)
                .toList();
    }
}
