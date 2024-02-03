package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.entity.QuizChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizChoiceRepository extends JpaRepository<QuizChoice, Long> {
    Optional<QuizChoice> findById(Long quizChoiceId);

    List<QuizChoice> findQuizChoicesByQuiz(Quiz quiz);
}
