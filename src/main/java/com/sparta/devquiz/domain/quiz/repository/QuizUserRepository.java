package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.entity.UserQuizId;
import com.sparta.devquiz.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizUserRepository extends JpaRepository<UserQuiz, UserQuizId> {
    @Query("SELECT uq.id.quizId FROM UserQuiz uq WHERE uq.user = :user AND uq.status = 'CORRECT'")
    List<Long> findCorrectQuizIdsByUser(@Param("user") User user);

}
