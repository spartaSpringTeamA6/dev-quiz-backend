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
    return userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(
            () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  public User getUserByOauthId(String oauthId) {
    return userRepository.findByOauthIdAndIsDeletedFalse(oauthId).orElseThrow(
            () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  public User getUserByNickname(String nickname){
    return userRepository.findByNicknameAndIsDeletedFalse(nickname).orElseThrow(
            () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  public boolean isExistedNickname(String nickname){
    return userRepository.existsByNickname(nickname);
  }
}
