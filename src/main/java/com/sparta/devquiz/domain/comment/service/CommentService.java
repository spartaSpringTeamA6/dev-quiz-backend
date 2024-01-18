package com.sparta.devquiz.domain.comment.service;

import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.exception.BoardCustomException;
import com.sparta.devquiz.domain.board.exception.BoardExceptionCode;
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
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
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
        Board board = getBoardById(boardId);

        if(Boolean.TRUE.equals(board.getIsDeleted())) {
            throw new BoardCustomException(BoardExceptionCode.ALREADY_DELETED_BOARD);
        }

        List<Comment> comments = commentRepository.findAllByBoardIdAndIsDeletedFalse(boardId);
        return CommentDetailsResponse.of(comments);
    }


    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest, User user) {
        Comment comment = getCommentById(commentId);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentCustomException(CommentExceptionCode.UNAUTHORIZED_USER);
        }

        comment.updateContent(commentUpdateRequest.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = getCommentById(commentId);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentCustomException(CommentExceptionCode.UNAUTHORIZED_USER);
        }

        comment.setDeleted(true);
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
