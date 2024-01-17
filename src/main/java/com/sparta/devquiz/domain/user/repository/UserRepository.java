package com.sparta.devquiz.domain.user.repository;

import com.sparta.devquiz.domain.user.dto.response.WeekScoreResponse;
import com.sparta.devquiz.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByIdAndIsDeletedFalse(Long id);
  Optional<User> findByOauthIdAndIsDeletedFalse(String oauthId);
  Optional<User> findByUsernameAndIsDeletedFalse(String username);
  boolean existsByUsername(String username);
  WeekScoreResponse findWeekScoreByIdAndIsDeletedFalse(Long id);

  @Query("SELECT uq.userQuizId.quizId FROM UserQuiz uq WHERE uq.user = :user AND uq.status = 'CORRECT'")
  List<Long> findCorrectQuizIdsByUser(@Param("user") User user);
}