package com.sparta.devquiz.domain.board.service;

import com.sparta.devquiz.domain.board.dto.ResponseDto.BoardListGetResponseDto;
import com.sparta.devquiz.domain.board.dto.RequestDto.BoardRequestDto;
import com.sparta.devquiz.domain.board.dto.ResponseDto.BoardSingleGetResponseDto;
import com.sparta.devquiz.domain.board.dto.RequestDto.BoardUpdateRequestDto;
import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404", "Quiz not found"));

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
                .orElseThrow(() -> new CustomException(HttpStatus.NO_CONTENT, "404", "Board not found"));

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
    public void updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404", "Board not found"));

        board.updateTitleAndContent(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
    }

    @Transactional
    public void deleteBoard(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "404", "Board not found"));

        board.setDeleted(true);
        boardRepository.save(board);
    }
}
