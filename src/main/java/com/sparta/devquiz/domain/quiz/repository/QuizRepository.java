package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.category.entity.Category;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.category.enums.QuizCategory;
import com.sparta.devquiz.domain.quiz.exception.QuizCustomException;
import com.sparta.devquiz.domain.quiz.exception.QuizExceptionCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findById(Long quizId);

    List<Quiz> findQuizByCategory(Category category, Pageable pageable);

    @Query("SELECT q FROM Quiz q WHERE q.category = :category AND q.id NOT IN :excludedQuizIds AND q.isDeleted = false ORDER BY FUNCTION('RAND')")
    List<Quiz> findQuizzesByCategoryExcludingIds(Category category, List<Long> excludedQuizIds, Pageable pageable);

    @Query("SELECT q FROM Quiz q WHERE q.category = :category AND q.isDeleted = false ORDER BY FUNCTION('RAND')")
    List<Quiz> findQuizzesByCategoryExcludingIds(Category category, Pageable pageable);

    Long countByCategory(QuizCategory category);


    default Quiz findQuizByIdOrElseThrow(Long quizId) {
        return findById(quizId)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));
    }
}