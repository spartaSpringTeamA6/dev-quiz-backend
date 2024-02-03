package com.sparta.devquiz.domain.user.repository;

import com.sparta.devquiz.domain.user.dto.response.WeekScoreResponse;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByIdAndIsDeletedFalse(Long id);
  Optional<User> findByOauthIdAndIsDeletedFalse(String oauthId);
  Optional<User> findByUsernameAndIsDeletedFalse(String username);
  boolean existsByUsername(String username);

  @Modifying(clearAutomatically = true)
  @Query("update User u set u.weekScore = 0")
  int updateWeekScore();

  WeekScoreResponse findWeekScoreByIdAndIsDeletedFalse(Long id);

  default User findByIdOrElseThrow(Long userId) {
    return findByIdAndIsDeletedFalse(userId).orElseThrow(
        () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  default User findByOauthIdOrElseThrow(String oauthId) {
    return findByOauthIdAndIsDeletedFalse(oauthId).orElseThrow(
        () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  default User findByUsernameOrElseThrow(String username) {
    return findByUsernameAndIsDeletedFalse(username).orElseThrow(
        () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

}