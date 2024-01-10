package com.sparta.devquiz.domain.board.service;

import com.sparta.devquiz.domain.board.dto.BoardRequestDto;
import com.sparta.devquiz.domain.board.dto.BoardSingleGetResponseDto;
import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.exception.QuizNotFoundException;
import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    @Transactional
    public Board createBoard(Long quizId, BoardRequestDto boardRequestDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // new CustomException(HttpStatus.NOT_FOUND, "404", "Quiz not found"));

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

}
