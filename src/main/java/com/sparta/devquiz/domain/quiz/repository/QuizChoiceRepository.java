package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.entity.QuizChoice;
import com.sparta.devquiz.domain.quiz.exception.QuizChoiceCustomException;
import com.sparta.devquiz.domain.quiz.exception.QuizChoiceExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizChoiceRepository extends JpaRepository<QuizChoice, Long> {

    @Query("SELECT qc FROM QuizChoice qc WHERE qc.quiz.id = :quizId AND qc.choiceSequence = :choiceSequence")
    Optional<QuizChoice> findByQuizIdAndChoiceSequence(Long quizId, int choiceSequence);

    List<QuizChoice> findQuizChoicesByQuiz(Quiz quiz);

    default QuizChoice findByQuizChoiceByChoiceSequenceOrElseThrow(Long quizId, int choiceSequence) {
        return findByQuizIdAndChoiceSequence(quizId, choiceSequence).orElseThrow(
                () -> new QuizChoiceCustomException(QuizChoiceExceptionCode.BAD_REQUEST_QUIZ_CHOICE)
        );
    }
}