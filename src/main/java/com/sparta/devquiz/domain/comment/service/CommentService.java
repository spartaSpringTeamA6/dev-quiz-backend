package com.sparta.devquiz.domain.comment.service;

import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.comment.dto.request.CommentCreateRequest;
import com.sparta.devquiz.domain.comment.dto.request.CommentUpdateRequest;
import com.sparta.devquiz.domain.comment.dto.response.CommentCreateResponse;
import com.sparta.devquiz.domain.comment.dto.response.CommentDetailsResponse;
import com.sparta.devquiz.domain.comment.entity.Comment;
import com.sparta.devquiz.domain.comment.entity.CommentLike;
import com.sparta.devquiz.domain.comment.entity.CommentLikeId;
import com.sparta.devquiz.domain.comment.exception.CommentCustomException;
import com.sparta.devquiz.domain.comment.exception.CommentExceptionCode;
import com.sparta.devquiz.domain.comment.repository.CommentLikeRepository;
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
    private final CommentLikeRepository commentLikeRepository;

    public CommentCreateResponse createComment(Long boardId,CommentCreateRequest commentCreateResponseDto, User user) {
        Board board = getBoardById(boardId);

        Comment comment = Comment.builder()
                .user(user)
                .board(board)
                .content(commentCreateResponseDto.getContent())
                .isDeleted(false)
                .build();
        commentRepository.save(comment);

        return CommentCreateResponse.of(comment);
    }

    public List<CommentDetailsResponse> getCommentList(Long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);

        return CommentDetailsResponse.of(comments);
    }


    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest, User user) {
        Comment comment = getCommentById(commentId);

        if (!comment.getUser().equals(user)) {
            throw new CommentCustomException(CommentExceptionCode.UNAUTHORIZED_USER);
        }

        comment.updateContent(commentUpdateRequest.getContent());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = getCommentById(commentId);

        if (!comment.getUser().equals(user)) {
            throw new CommentCustomException(CommentExceptionCode.UNAUTHORIZED_USER);
        }

        comment.setDeleted(true);
        commentRepository.save(comment);
    }

    @Transactional
    public void likeComment(Long commentId, User user) {
        Comment comment = getCommentById(commentId);

        CommentLikeId commentLikeId = new CommentLikeId(user.getId(), commentId);

        if (commentLikeRepository.existsById(commentLikeId)) {
            throw new CommentCustomException(CommentExceptionCode.ALREADY_LIKED);
        }

        CommentLike commentLike = CommentLike.builder()
                .commentLikeId(commentLikeId)
                .user(user)
                .comment(comment)
                .build();

        commentLikeRepository.save(commentLike);
    }

    @Transactional
    public void unlikeComment(Long commentId, User user) {
        CommentLikeId commentLikeId = new CommentLikeId(user.getId(), commentId);

        if (commentLikeRepository.existsById(commentLikeId)) {
            commentLikeRepository.deleteById(commentLikeId);
        } else {
            throw new CommentCustomException(CommentExceptionCode.NOT_LIKED);
        }
    }

    private Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new CommentCustomException(CommentExceptionCode.NOT_FOUND_BOARD));
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentCustomException(CommentExceptionCode.NOT_FOUND_COMMENT));
    }


}
