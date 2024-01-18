package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizUserRepository extends JpaRepository<UserQuiz, Long> {
    @Query("SELECT uq.quiz.id FROM UserQuiz uq WHERE uq.user = :user AND uq.status = 'CORRECT' AND uq.quiz.isDeleted = false")
    List<Long> findCorrectQuizIdsByUser(@Param("user") User user);

    @Query("SELECT uq.quiz FROM UserQuiz uq WHERE uq.user = :user AND uq.status = 'CORRECT' AND uq.quiz.isDeleted = false")
    List<Quiz> findCorrectQuizzesByUser(@Param("user") User user);

    @Query("SELECT uq.quiz FROM UserQuiz uq WHERE uq.user = :user AND uq.status = 'FAIL' AND uq.quiz.isDeleted = false")
    List<Quiz> findFAILQuizzesByUser(@Param("user") User user);

    @Query("SELECT uq.quiz FROM UserQuiz uq WHERE uq.user = :user AND uq.status = 'PASS' AND uq.quiz.isDeleted = false")
    List<Quiz> findPASSQuizzesByUser(@Param("user") User user);
}
