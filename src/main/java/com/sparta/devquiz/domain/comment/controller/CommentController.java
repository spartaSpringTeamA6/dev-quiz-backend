package com.sparta.devquiz.domain.comment.controller;

import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.comment.dto.requestDto.CommentCreateRequestDto;
import com.sparta.devquiz.domain.comment.dto.responseDto.CommentCreateResponseDto;
import com.sparta.devquiz.domain.comment.entity.Comment;
import com.sparta.devquiz.domain.comment.repository.CommentRepository;
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

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
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



}
