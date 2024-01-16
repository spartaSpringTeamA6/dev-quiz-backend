package com.sparta.devquiz.domain.board.controller;

import com.sparta.devquiz.domain.board.dto.request.BoardCreateRequest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "4. Board API", description = "Board 관련 API 입니다.")
public class BoardController {

    private final BoardService boardService;

    @Operation(operationId = "Board-001", summary = "Board 생성")
    @PostMapping("/quizzes/{quiz_id}/boards")
    public ResponseEntity<CommonResponseDto> createBoard(
            @PathVariable("quiz_id") Long quizId,
            @Valid @RequestBody BoardCreateRequest boardRequest,
            @AuthUser User user
    ) {
        BoardCreateResponse response = boardService.createBoard(quizId, boardRequest, user);

        return ResponseEntity
                .status(BoardResponseCode.CREATED_BOARD.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.CREATED_BOARD, response));
    }

    @Operation(operationId = "Board-002", summary = "단일 Board 조회")
    @GetMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> getBoard(
            @PathVariable("board_id") Long boardId
    ) {
        BoardDetailsResponse response = boardService.getBoard(boardId);

        return ResponseEntity
                .status(BoardResponseCode.OK_GET_BOARD_INFO.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_GET_BOARD_INFO, response));
    }

    @Operation(operationId = "Board-003", summary = "Board 리스트 조회")
    @GetMapping("/quizzes/{quiz_id}/boards")
    public ResponseEntity<CommonResponseDto> getBoardList(
            @PathVariable("quiz_id") Long quizId
    ){
        List<BoardDetailsResponse> boardDetailsResponse = boardService.getBoardList(quizId);

        return ResponseEntity
                .status(BoardResponseCode.OK_GET_BOARDLIST_INFO.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_GET_BOARD_INFO, boardDetailsResponse));
    }

    @Operation(operationId = "Board-004", summary = "Board 수정")
    @PatchMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> updateBoard(
            @PathVariable("board_id") Long boardId,
            @Valid @RequestBody BoardUpdateRequest boardUpdateRequest,
            @AuthUser User user
    ) {
        boardService.updateBoard(boardId, boardUpdateRequest, user);

        return ResponseEntity
                .status(BoardResponseCode.OK_UPDATE_BOARD.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_UPDATE_BOARD));
    }

    @Operation(operationId = "Board-005",summary = "Board 삭제")
    @DeleteMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> deleteBoard(
            @PathVariable("board_id") Long boardId,
            @AuthUser User user
    ) {
        boardService.deleteBoard(boardId, user);

        return ResponseEntity
                .status(BoardResponseCode.OK_DELETE_BOARD.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_DELETE_BOARD));
    }

}
