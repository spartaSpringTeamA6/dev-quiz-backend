package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.entity.UserQuizId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizUserRepository extends JpaRepository<UserQuiz, UserQuizId> {

}
