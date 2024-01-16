package com.sparta.devquiz.domain.comment.controller;

import com.sparta.devquiz.domain.comment.dto.request.CommentCreateRequest;
import com.sparta.devquiz.domain.comment.dto.request.CommentUpdateRequest;
import com.sparta.devquiz.domain.comment.dto.response.CommentCreateResponse;
import com.sparta.devquiz.domain.comment.dto.response.CommentDetailsResponse;
import com.sparta.devquiz.domain.comment.response.CommentResponseCode;
import com.sparta.devquiz.domain.comment.service.CommentService;
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
@Tag(name = "4. Comment API", description = "Comment 관련 API 입니다.")
public class CommentController {

    private final CommentService commentService;

    @Operation(operationId = "Comment-001", summary = "Comment 작성")
    @PostMapping("/boards/{board_id}/comments")
    public ResponseEntity<CommonResponseDto> createComment(
            @PathVariable("board_id") Long boardId,
            @Valid @RequestBody CommentCreateRequest commentCreateRequest,
            @AuthUser User user
    ) {
        CommentCreateResponse response = commentService.createComment(boardId, commentCreateRequest, user);

        return ResponseEntity
                .status(CommentResponseCode.CREATED_COMMENT.getHttpStatus())
                .body(CommonResponseDto.of(CommentResponseCode.CREATED_COMMENT, response));
    }

    @Operation(operationId = "Comment-002", summary = "Board에 속한 모든 Comment 조회")
    @GetMapping("/boards/{board_id}/comments")
    public ResponseEntity<CommonResponseDto> getCommentList(
            @PathVariable("board_id") Long boardId
    ) {
        List<CommentDetailsResponse> response = commentService.getCommentList(boardId);

        return ResponseEntity
                .status(CommentResponseCode.OK_GET_ALL_COMMENT.getHttpStatus())
                .body(CommonResponseDto.of(CommentResponseCode.OK_GET_ALL_COMMENT, response));
    }

    @Operation(operationId = "Comment-003", summary = "Comment 수정")
    @PatchMapping("/comments/{comment_id}")
    public ResponseEntity<CommonResponseDto> updateComment(
            @PathVariable("comment_id") Long commentId,
            @Valid @RequestBody CommentUpdateRequest commentUpdateRequest,
            @AuthUser User user
    ) {
        commentService.updateComment(commentId, commentUpdateRequest, user);

        return ResponseEntity
                .status(CommentResponseCode.OK_UPDATE_COMMENT.getHttpStatus())
                .body(CommonResponseDto.of(CommentResponseCode.OK_UPDATE_COMMENT));
    }

    @Operation(operationId = "Comment-004", summary = "Comment 삭제")
    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<CommonResponseDto> deleteComment(
            @PathVariable("comment_id") Long commentId,
            @AuthUser User user
    ) {
        commentService.deleteComment(commentId, user);

        return ResponseEntity
                .status(CommentResponseCode.OK_DELETE_COMMENT.getHttpStatus())
                .body(CommonResponseDto.of(CommentResponseCode.OK_DELETE_COMMENT));
    }


    @Operation(operationId = "Comment-005", summary = "댓글 좋아요")
    @PostMapping("/comments/{comment_id}/like")
    public ResponseEntity<CommonResponseDto> likeComment(
            @PathVariable("comment_id") Long commentId,
            @AuthUser User user
    ) {
        commentService.likeComment(commentId, user);

        return ResponseEntity
                .status(CommentResponseCode.CREATED_LIKE.getHttpStatus())
                .body(CommonResponseDto.of(CommentResponseCode.CREATED_LIKE));
    }

    @Operation(operationId = "Comment-006", summary = "댓글 좋아요 취소")
    @DeleteMapping("/comments/{comment_id}/unlike")
    public ResponseEntity<CommonResponseDto> unlikeComment(
            @PathVariable("comment_id") Long commentId,
            @AuthUser User user
    ) {
        commentService.unlikeComment(commentId, user);

        return ResponseEntity
                .status(CommentResponseCode.OK_DELETE_LIKE.getHttpStatus())
                .body(CommonResponseDto.of(CommentResponseCode.OK_DELETE_LIKE));
    }

}
