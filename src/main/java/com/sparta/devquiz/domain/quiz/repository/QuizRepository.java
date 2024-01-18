package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findById(Long quizid);

    Page<Quiz> findQuizByCategory(QuizCategory category, Pageable pageable);

    @Query("SELECT q FROM Quiz q WHERE q.category = :category AND q.id NOT IN :excludedQuizIds ORDER BY FUNCTION('RAND')")
    List<Quiz> findQuizzesByCategoryExcludingIds(QuizCategory category, List<Long> excludedQuizIds, Pageable pageable);
}
