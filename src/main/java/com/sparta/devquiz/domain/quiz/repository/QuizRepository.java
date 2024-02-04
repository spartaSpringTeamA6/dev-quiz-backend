package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.domain.quiz.exception.QuizCustomException;
import com.sparta.devquiz.domain.quiz.exception.QuizExceptionCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findQuizByCategory(QuizCategory category, Pageable pageable);

    @Query("SELECT q FROM Quiz q WHERE q.category = :category AND q.id NOT IN :excludedQuizIds AND q.isDeleted = false ORDER BY FUNCTION('RAND')")
    List<Quiz> findQuizzesByCategoryExcludingIds(QuizCategory category, List<Long> excludedQuizIds, Pageable pageable);

    @Query("SELECT q FROM Quiz q WHERE q.category = :category AND q.isDeleted = false ORDER BY FUNCTION('RAND')")
    List<Quiz> findQuizzesByCategoryExcludingIds(QuizCategory category, Pageable pageable);

    default Quiz findQuizByIdOrElseThrow(Long quizId) {
        return findById(quizId)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));
    }
}