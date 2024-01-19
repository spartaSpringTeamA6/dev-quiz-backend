package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.quiz.dto.response.QuizGetByUserResponse;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.user.entity.User;
import java.util.List;

public interface QuizUserRepositoryCustom {
    List<QuizGetByUserResponse> findCorrectQuizzesByUsers(User loginUser);
    List<QuizGetByUserResponse> findCorrectQuizzesByUsers(User loginUser,
            UserQuizStatus status);
}
