package com.sparta.devquiz.domain.board.dto.response;

import com.sparta.devquiz.domain.board.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "보드 생성 응답 Dto")
public class BoardCreateResponse {

    @Column
    @Schema(description = "보드 ID", defaultValue = "1")
    private Long boardId;

    @Column
    @Schema(description = "보드 이름", defaultValue = "날아라 슈퍼보드")
    private String title;

    @Column
    @Schema(description = "보드 설명", defaultValue = "치키치키차카차카")
    private String content;

    public static BoardCreateResponse of(Board board) {
        return BoardCreateResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }

}