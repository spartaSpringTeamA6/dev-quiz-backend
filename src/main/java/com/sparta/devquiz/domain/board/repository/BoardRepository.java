package com.sparta.devquiz.domain.board.repository;

import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.exception.BoardCustomException;
import com.sparta.devquiz.domain.board.exception.BoardExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {
        List<Board> findAllByQuizIdAndIsDeletedFalse(Long quizId);

        @Query("select b from Board b where b.user.id = :userId and b.isDeleted = FALSE")
        List<Board> findAllByUserIdAndIsDeletedFalse(Long userId);

        default Board findBoardByIdOrElseThrow(Long boardId) {
                return findById(boardId)
                        .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD));
        }
}