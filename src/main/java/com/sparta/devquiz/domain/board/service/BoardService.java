package com.sparta.devquiz.domain.board.service;

import com.sparta.devquiz.domain.board.dto.requestDto.BoardRequestDto;
import com.sparta.devquiz.domain.board.dto.requestDto.BoardUpdateRequestDto;
import com.sparta.devquiz.domain.board.dto.responseDto.BoardListGetResponseDto;
import com.sparta.devquiz.domain.board.dto.responseDto.BoardSingleGetResponseDto;
import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.exception.BoardCustomException;
import com.sparta.devquiz.domain.board.exception.BoardExceptionCode;
import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final QuizRepository quizRepository;

    @Transactional
    public Board createBoard(Long quizId, BoardRequestDto boardRequestDto, User user) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_QUIZ));

        Board board = Board.builder()
                .user(user)
                .quiz(quiz)
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .isDeleted(false)
                .build();

        return boardRepository.save(board);
    }

    public BoardSingleGetResponseDto getSingleBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD));

        return new BoardSingleGetResponseDto(board.getId(), board.getTitle(), board.getContent());
    }

    public BoardListGetResponseDto getBoardList(Long quizId) {

        List<Board> boards = boardRepository.findAllBoardByQuizId(quizId);

        List<BoardSingleGetResponseDto> boardSingleGetResponseDtoList = boards.stream()
                .map(board -> new BoardSingleGetResponseDto(board.getId(), board.getTitle(), board.getContent()))
                .collect(Collectors.toList());

        return new BoardListGetResponseDto(boardSingleGetResponseDtoList);
    }

    @Transactional
    public void updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto, User currentUser) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD));

        if (!board.getUser().equals(currentUser)) {
            throw new BoardCustomException(BoardExceptionCode.UNAUTHORIZED_USER);
        }

        board.updateTitleAndContent(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
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
