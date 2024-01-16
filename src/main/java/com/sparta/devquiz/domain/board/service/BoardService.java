package com.sparta.devquiz.domain.board.service;

import com.sparta.devquiz.domain.board.dto.request.BoardCreateRequest;
import com.sparta.devquiz.domain.board.dto.request.BoardUpdateRequest;
import com.sparta.devquiz.domain.board.dto.response.*;
import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.exception.BoardCustomException;
import com.sparta.devquiz.domain.board.exception.BoardExceptionCode;
import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.quiz.QuizRepository;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final QuizRepository quizRepository;

    public BoardCreateResponse createBoard(Long quizId, BoardCreateRequest request, User user) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_QUIZ));

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
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD));

        return BoardDetailsResponse.of(board);
    }

    public List<BoardDetailsResponse> getBoardList(Long quizId) {
        List<Board> boards = boardRepository.findAllByQuizId(quizId);

        return BoardDetailsResponse.of(boards);
    }

    @Transactional
    public void updateBoard(Long boardId, BoardUpdateRequest request, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD));

        if (!board.getUser().equals(user)) {
            throw new BoardCustomException(BoardExceptionCode.UNAUTHORIZED_USER);
        }

        board.updateTitleAndContent(request.getTitle(), request.getContent());
        boardRepository.save(board);
    }


    @Transactional
    public void deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD));

        if (!board.getUser().equals(user)) {
            throw new BoardCustomException(BoardExceptionCode.UNAUTHORIZED_USER);
        }

        board.setDeleted(true);
        boardRepository.save(board);
    }

}
