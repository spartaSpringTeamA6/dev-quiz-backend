package com.sparta.devquiz.domain.board.controller;

import com.sparta.devquiz.domain.board.dto.BoardCreateResponseDto;
import com.sparta.devquiz.domain.board.dto.BoardRequestDto;
import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.response.BoardResponseCode;
import com.sparta.devquiz.domain.board.service.BoardService;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BOARD", description = "Board API")
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "Board 작성")
    @PostMapping("/api/quizzes/{quiz_id}/boards")
    public ResponseEntity<CommonResponseDto<BoardCreateResponseDto>> createBoard(
            @PathVariable Long quiz_id,
            @Valid @RequestBody BoardRequestDto boardRequestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Board board = boardService.createBoard(quiz_id, boardRequestDto, userDetails.getUsername());

        BoardCreateResponseDto boardCreateResponseDto = new BoardCreateResponseDto(board.getId(), board.getTitle(), board.getContent());

        CommonResponseDto<BoardCreateResponseDto> commonResponseDto = CommonResponseDto.of(
                BoardResponseCode.CREATED_BOARD.getHttpStatus(),
                BoardResponseCode.CREATED_BOARD.getMessage(),
                boardCreateResponseDto
        );

        return new ResponseEntity<>(commonResponseDto, HttpStatus.CREATED);
    }


}
