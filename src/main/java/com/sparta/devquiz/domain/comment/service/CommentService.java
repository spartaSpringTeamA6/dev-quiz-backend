package com.sparta.devquiz.domain.comment.service;

import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.comment.dto.requestDto.CommentCreateRequestDto;
import com.sparta.devquiz.domain.comment.entity.Comment;
import com.sparta.devquiz.domain.comment.exception.CommentCustomException;
import com.sparta.devquiz.domain.comment.exception.CommentExceptionCode;
import com.sparta.devquiz.domain.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sparta.devquiz.domain.user.entity.User;

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

}
