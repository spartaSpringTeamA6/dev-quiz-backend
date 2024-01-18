package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.dto.response.QuizGetQuizzesByUserResponse;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.user.entity.User;
import java.util.List;

public interface QuizUserRepositoryCustom {
    List<QuizGetQuizzesByUserResponse> findCorrectQuizzesByUsers(User loginUser);
    List<QuizGetQuizzesByUserResponse> findCorrectQuizzesByUsers(User loginUser,
            UserQuizStatus status);
}
