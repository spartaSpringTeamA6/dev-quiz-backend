package com.sparta.devquiz.domain.board.service;

import com.sparta.devquiz.domain.board.dto.request.BoardCreateRequest;
import com.sparta.devquiz.domain.board.dto.request.BoardUpdateRequest;
import com.sparta.devquiz.domain.board.dto.response.BoardCreateResponse;
import com.sparta.devquiz.domain.board.dto.response.BoardDetailsResponse;
import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.exception.BoardCustomException;
import com.sparta.devquiz.domain.board.exception.BoardExceptionCode;
import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.comment.entity.Comment;
import com.sparta.devquiz.domain.quiz.repository.QuizRepository;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final QuizRepository quizRepository;

    @Transactional
    public BoardCreateResponse createBoard(Long quizId, BoardCreateRequest request, User user) {
        Quiz quiz = quizRepository.findQuizByIdOrElseThrow(quizId);

        Board board = Board.builder()
                .user(user)
                .quiz(quiz)
                .title(request.getTitle())
                .content(request.getContent())
                .isDeleted(false)
                .build();
        boardRepository.save(board);

        return BoardCreateResponse.of(board);
    }

    public BoardDetailsResponse getBoard(Long boardId) {
        Board board = boardRepository.findBoardByIdOrElseThrow(boardId);
        isExistsBoard(board);

        return BoardDetailsResponse.of(board);
    }

    public List<BoardDetailsResponse> getBoardList(Long quizId) {
        List<Board> boards = boardRepository.findAllByQuizIdAndIsDeletedFalse(quizId);

        return BoardDetailsResponse.of(boards);
    }

    @Transactional
    public void updateBoard(Long boardId, BoardUpdateRequest request, User user) {
        Board board = boardRepository.findBoardByIdOrElseThrow(boardId);
        isExistsBoardCreatedAt(board,user);

        board.updateTitleAndContent(request.getTitle(), request.getContent());
    }

    @Transactional
    public void deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD));

        isExistsBoard(board);
        isExistsBoardCreatedAt(board,user);

        for (Comment comment : board.getComments()) {
            comment.setDeleted(true);
        }
        board.setDeleted(true);
    }

    public void isExistsBoardCreatedAt(Board board, User user){
        if (!board.getUser().getId().equals(user.getId())) {
            throw new BoardCustomException(BoardExceptionCode.UNAUTHORIZED_USER);
        }
    }

    public void isExistsBoard(Board board){
        if(Boolean.TRUE.equals(board.getIsDeleted())) {
            throw new BoardCustomException(BoardExceptionCode.ALREADY_DELETED_BOARD);
        }
    }

}
