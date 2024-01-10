package com.sparta.devquiz.domain.user.repository;

import com.sparta.devquiz.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByOauthId(String oauthId);
}
