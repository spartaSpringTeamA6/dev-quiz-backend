package com.sparta.devquiz.domain.user.service;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;


  public User getUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(
        () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  public User getUserByOauthId(String oauthId) {
    return userRepository.findByOauthId(oauthId).orElseThrow(
        () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }
}
