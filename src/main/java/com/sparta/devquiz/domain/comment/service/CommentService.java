package com.sparta.devquiz.domain.comment.service;

import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.comment.dto.requestDto.CommentCreateRequestDto;
import com.sparta.devquiz.domain.comment.dto.requestDto.CommentUpdateRequestDto;
import com.sparta.devquiz.domain.comment.dto.responseDto.CommentListGetResponseDto;
import com.sparta.devquiz.domain.comment.dto.responseDto.CommentSingleGetResponseDto;
import com.sparta.devquiz.domain.comment.entity.Comment;
import com.sparta.devquiz.domain.comment.exception.CommentCustomException;
import com.sparta.devquiz.domain.comment.exception.CommentExceptionCode;
import com.sparta.devquiz.domain.comment.repository.CommentRepository;
import com.sparta.devquiz.domain.user.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(Long boardId, @Valid CommentCreateRequestDto commentCreateResponseDto, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommentCustomException(CommentExceptionCode.NOT_FOUND_BOARD));

        Comment comment = Comment.builder()
                .user(user)
                .board(board)
                .content(commentCreateResponseDto.getContent())
                .isDeleted(false)
                .build();

        return commentRepository.save(comment);
    }

    public CommentListGetResponseDto getCommentList(Long board_id) {

        List<Comment> comments = commentRepository.findAllCommentsByBoardId(board_id);

        List<CommentSingleGetResponseDto> commentListDto = comments.stream()
                .map(comment -> new CommentSingleGetResponseDto(comment.getContent()))
                .toList();

        return new CommentListGetResponseDto(commentListDto);
    }

    @Transactional
    public void updateComment(Long comment_id, CommentUpdateRequestDto commentUpdateRequestDto, User user) {
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new CommentCustomException(CommentExceptionCode.NOT_FOUND_COMMENT));

        if (!comment.getUser().equals(user)) {
            throw new CommentCustomException(CommentExceptionCode.UNAUTHORIZED_USER);
        }

        comment.updateContent(commentUpdateRequestDto.getContent());
    }

    @Transactional
    public void deleteComment(Long comment_id, User user) {
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new CommentCustomException(CommentExceptionCode.NOT_FOUND_COMMENT));

        if (!comment.getUser().equals(user)) {
            throw new CommentCustomException(CommentExceptionCode.UNAUTHORIZED_USER);
        }

        comment.setDeleted(true);
        commentRepository.save(comment);
    }


}
