package com.sparta.devquiz.domain.board.controller;

import com.sparta.devquiz.domain.board.dto.request.BoardRequest;
import com.sparta.devquiz.domain.board.dto.request.BoardUpdateRequest;
import com.sparta.devquiz.domain.board.dto.response.BoardCreateResponse;
import com.sparta.devquiz.domain.board.dto.response.BoardDetailsResponse;
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

import java.util.List;

@Tag(name = "BOARD", description = "Board API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(operationId = "Board-001", summary = "Board 생성")
    @PostMapping("/quizzes/{quiz_id}/boards")
    public ResponseEntity<CommonResponseDto> createBoard(
            @PathVariable Long quiz_id,
            @Valid @RequestBody BoardRequest boardRequestDto,
            @AuthUser User user
    ) {
        BoardCreateResponse boardCreateResponseDto = boardService.createBoard(quiz_id, boardRequestDto, user);

        return ResponseEntity
                .status(BoardResponseCode.CREATED_BOARD.getHttpStatus())
                        .body(CommonResponseDto.of(BoardResponseCode.CREATED_BOARD, boardCreateResponseDto));
    }

    @Operation(operationId = "Board-002", summary = "단일 Board 조회")
    @GetMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> getBoard(
            @PathVariable Long board_id
    ) {
        BoardDetailsResponse boardDetailsResponse = boardService.getBoard(board_id);

        return ResponseEntity
                .status(BoardResponseCode.OK_GET_BOARD_INFO.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_GET_BOARD_INFO, boardDetailsResponse));
    }

    @Operation(operationId = "Board-003", summary = "Board 리스트 조회")
    @GetMapping("/quizzes/{quiz_id}/boards")
    public ResponseEntity<CommonResponseDto> getBoardList(
            @PathVariable Long quiz_id
    ){
        List<BoardDetailsResponse> boardDetailsResponse = boardService.getBoardList(quiz_id);

        return ResponseEntity
                .status(BoardResponseCode.OK_GET_BOARDLIST_INFO.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_GET_BOARD_INFO, boardDetailsResponse));
    }

    @Operation(summary = "Board 수정")
    @PatchMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> updateBoard(
            @PathVariable Long board_id,
            @Valid @RequestBody BoardUpdateRequest boardUpdateRequestDto,
            @AuthUser User user
    ) {
        boardService.updateBoard(board_id, boardUpdateRequestDto, user);

        return ResponseEntity
                .status(BoardResponseCode.NO_CONTENT_UPDATE_BOARD.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.NO_CONTENT_UPDATE_BOARD));
    }

    @Operation(summary = "Board 삭제")
    @DeleteMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> deleteBoard(
            @PathVariable Long board_id,
            @AuthUser User user
    ) {
        boardService.deleteBoard(board_id, user);

        return ResponseEntity
                .status(BoardResponseCode.NO_CONTENT_DELETE_BOARD.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.NO_CONTENT_DELETE_BOARD));
    }




}
