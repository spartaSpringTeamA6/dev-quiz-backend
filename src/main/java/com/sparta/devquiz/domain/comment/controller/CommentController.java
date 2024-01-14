package com.sparta.devquiz.domain.comment.controller;

import com.sparta.devquiz.domain.comment.dto.requestDto.CommentCreateRequestDto;
import com.sparta.devquiz.domain.comment.dto.requestDto.CommentUpdateRequestDto;
import com.sparta.devquiz.domain.comment.dto.responseDto.CommentCreateResponseDto;
import com.sparta.devquiz.domain.comment.dto.responseDto.CommentListGetResponseDto;
import com.sparta.devquiz.domain.comment.entity.Comment;
import com.sparta.devquiz.domain.comment.response.CommentResponseCode;
import com.sparta.devquiz.domain.comment.service.CommentService;
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

@Tag(name = "COMMENT", description = "Comment API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Comment 작성")
    @PostMapping("boards/{board_id}/comments")
    public ResponseEntity<CommonResponseDto<CommentCreateResponseDto>> createComment(@PathVariable Long board_id,
                                                                                     @Valid @RequestBody CommentCreateRequestDto commentCreateRequestDto,
                                                                                     @AuthUser User user) {

        Comment comment = commentService.createComment(board_id, commentCreateRequestDto, user);

        CommentCreateResponseDto  commentCreateResponseDto = new CommentCreateResponseDto(comment.getContent());

        return new ResponseEntity<>(
                new CommonResponseDto<>(
                        CommentResponseCode.CREATED_COMMENT.getHttpStatus(),
                        CommentResponseCode.CREATED_COMMENT.getMessage(),
                        commentCreateResponseDto
                ),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Board에 속한 모든 Comment 조회")
    @GetMapping("/boards/{board_id}/comments")
    public ResponseEntity<CommonResponseDto<CommentListGetResponseDto>> getBoardList(@PathVariable Long board_id) {
        CommentListGetResponseDto commentListGetResponseDto = commentService.getCommentList(board_id);

        return new ResponseEntity<>(
                new CommonResponseDto<>(
                        CommentResponseCode.OK_GET_ALL_COMMENT.getHttpStatus(),
                        CommentResponseCode.OK_GET_ALL_COMMENT.getMessage(),
                        commentListGetResponseDto
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Comment 수정")
    @PatchMapping("/comments/{comment_id}")
    public ResponseEntity<CommonResponseDto<Void>> updateComment(@PathVariable Long comment_id,
                                                                 @Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
                                                                 @AuthUser User user) {
        commentService.updateComment(comment_id, commentUpdateRequestDto, user);

        return new ResponseEntity<>(
                new CommonResponseDto<>(
                        CommentResponseCode.NO_CONTENT_UPDATE_COMMENT.getHttpStatus(),
                        CommentResponseCode.NO_CONTENT_UPDATE_COMMENT.getMessage(),
                        null
                ),
                HttpStatus.NO_CONTENT
        );
    }

    @Operation(summary = "Comment 삭제")
    @PatchMapping("/comments/{comment_id}")
    public ResponseEntity<CommonResponseDto<Void>> deleteComment(@PathVariable Long comment_id,
                                                                 @AuthUser User user) {
        commentService.deleteComment(comment_id, user);

        return new ResponseEntity<>(
                new CommonResponseDto<>(
                        CommentResponseCode.NO_CONTENT_DELETE_COMMENT.getHttpStatus(),
                        CommentResponseCode.NO_CONTENT_DELETE_COMMENT.getMessage(),
                        null
                ),
                HttpStatus.NO_CONTENT
        );
    }

    @Operation(summary = "댓글 좋아요")
    @PostMapping("/comments/{comment_id}/like")
    public ResponseEntity<CommonResponseDto<Void>> likeComment(@PathVariable Long comment_id,
                                                               @AuthUser User user) {
        commentService.likeComment(comment_id, user);

        return new ResponseEntity<>(
                new CommonResponseDto<>(
                        CommentResponseCode.CREATED_LIKE.getHttpStatus(),
                        CommentResponseCode.CREATED_LIKE.getMessage(),
                        null
                ),
                HttpStatus.OK
        );
    }


}
