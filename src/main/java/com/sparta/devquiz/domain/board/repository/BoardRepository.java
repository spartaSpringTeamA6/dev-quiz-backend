package com.sparta.devquiz.domain.board.repository;

import com.sparta.devquiz.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
        List<Board> findAllByQuizIdAndIsDeletedFalse(Long quizId);
}
