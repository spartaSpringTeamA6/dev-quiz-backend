package com.sparta.devquiz.domain.board.controller;

import com.sparta.devquiz.domain.board.dto.BoardCreateResponseDto;
import com.sparta.devquiz.domain.board.dto.BoardRequestDto;
import com.sparta.devquiz.domain.board.dto.BoardSingleGetResponseDto;
import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.response.BoardResponseCode;
import com.sparta.devquiz.domain.board.service.BoardService;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.annotation.AuthUser;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BOARD", description = "Board API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "Board 작성")
    @PostMapping("/quizzes/{quiz_id}/boards")
    public ResponseEntity<CommonResponseDto<BoardCreateResponseDto>> createBoard(@PathVariable Long quiz_id,
                                                                                 @Valid @RequestBody BoardRequestDto boardRequestDto,
                                                                                 @AuthUser User user) {

        Board board = boardService.createBoard(quiz_id, boardRequestDto, user.getUsername());

        BoardCreateResponseDto boardCreateResponseDto = new BoardCreateResponseDto(board.getId(), board.getTitle(), board.getContent());

        CommonResponseDto<BoardCreateResponseDto> commonResponseDto = CommonResponseDto.of(
                BoardResponseCode.CREATED_BOARD.getHttpStatus(),
                BoardResponseCode.CREATED_BOARD.getMessage(),
                boardCreateResponseDto
        );

        return new ResponseEntity<>(commonResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "단일 Board 조회")
    @GetMapping("/api/boards/{board_id}")
    public ResponseEntity<CommonResponseDto<BoardSingleGetResponseDto>> getSingleBoard(
            @PathVariable Long board_id) {

        BoardSingleGetResponseDto boardSingleGetResponseDto = boardService.getSingleBoard(board_id);

        CommonResponseDto<BoardSingleGetResponseDto> commonResponseDto = CommonResponseDto.of(
                BoardResponseCode.OK_GET_BOARD_INFO.getHttpStatus(),
                BoardResponseCode.OK_GET_BOARD_INFO.getMessage(),
                boardSingleGetResponseDto
        );

        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }




}
