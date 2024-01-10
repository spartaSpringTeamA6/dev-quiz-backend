package com.sparta.devquiz.domain.board.controller;

import com.sparta.devquiz.domain.board.dto.*;
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

        Board board = boardService.createBoard(quiz_id, boardRequestDto, user);

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

    @Operation(summary = "퀴즈에 속한 모든 Board 조회")
    @GetMapping("/quizzes/{quiz_id}/boards")
    public ResponseEntity<CommonResponseDto<BoardListGetResponseDto>> getBoardList(@PathVariable Long quiz_id) {
        BoardListGetResponseDto boardListGetResponseDto = boardService.getBoardList(quiz_id);

        CommonResponseDto<BoardListGetResponseDto> commonResponseDto = CommonResponseDto.of(
                BoardResponseCode.OK_GET_BOARDLIST_INFO.getHttpStatus(),
                BoardResponseCode.OK_GET_BOARDLIST_INFO.getMessage(),
                boardListGetResponseDto
        );

        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Board 수정")
    @PatchMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto<Void>> updateBoard(@PathVariable Long board_id,
                                                               @Valid @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {

        boardService.updateBoard(board_id, boardUpdateRequestDto);

        CommonResponseDto<Void> commonResponseDto = CommonResponseDto.of(
                BoardResponseCode.NO_CONTENT_UPDATE_BOARD.getHttpStatus(),
                BoardResponseCode.NO_CONTENT_UPDATE_BOARD.getMessage(),
                null
        );

        return new ResponseEntity<>(commonResponseDto, HttpStatus.NO_CONTENT);
    }




}
