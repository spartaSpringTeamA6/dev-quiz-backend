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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "BOARD-001", summary = "보드 생성")
    @PostMapping("/quizzes/{quizId}/boards")
    public ResponseEntity<CommonResponseDto> createBoard(
            @PathVariable Long quizId,
            @Valid @RequestBody BoardCreateRequest boardRequest,
            @AuthUser User user
    ) {
        BoardCreateResponse response = boardService.createBoard(quizId, boardRequest, user);

        return ResponseEntity
                .status(BoardResponseCode.CREATED_BOARD.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.CREATED_BOARD, response));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "BOARD-002", summary = "단일 보드 조회")
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<CommonResponseDto> getBoard(
            @PathVariable Long boardId
    ) {
        BoardDetailsResponse response = boardService.getBoard(boardId);

        return ResponseEntity
                .status(BoardResponseCode.OK_GET_BOARD_INFO.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_GET_BOARD_INFO, response));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "BOARD-003", summary = "보드 리스트 조회")
    @GetMapping("/quizzes/{quizId}/boards")
    public ResponseEntity<CommonResponseDto> getBoardList(
            @PathVariable Long quizId
    ){
        List<BoardDetailsResponse> boardDetailsResponse = boardService.getBoardList(quizId);

        return ResponseEntity
                .status(BoardResponseCode.OK_GET_BOARDLIST_INFO.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_GET_BOARD_INFO, boardDetailsResponse));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "BOARD-004", summary = "보드 수정")
    @PatchMapping("/boards/{boardId}")
    public ResponseEntity<CommonResponseDto> updateBoard(
            @PathVariable Long boardId,
            @Valid @RequestBody BoardUpdateRequest boardUpdateRequest,
            @AuthUser User user
    ) {
        boardService.updateBoard(boardId, boardUpdateRequest, user);

        return ResponseEntity
                .status(BoardResponseCode.OK_UPDATE_BOARD.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_UPDATE_BOARD));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "BOARD-005",summary = "보드 삭제")
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<CommonResponseDto> deleteBoard(
            @PathVariable Long boardId,
            @AuthUser User user
    ) {
        boardService.deleteBoard(boardId, user);

        return ResponseEntity
                .status(BoardResponseCode.OK_DELETE_BOARD.getHttpStatus())
                .body(CommonResponseDto.of(BoardResponseCode.OK_DELETE_BOARD));
    }
}
