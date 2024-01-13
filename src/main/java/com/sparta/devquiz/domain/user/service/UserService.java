package com.sparta.devquiz.domain.user.service;

import com.sparta.devquiz.domain.user.dto.request.UserUpdateRequest;
import com.sparta.devquiz.domain.user.dto.response.UserDetailResponse;
import com.sparta.devquiz.domain.user.entity.Skill;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.UserSkill;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserDetailResponse getMyProfile(User user, Long userId) {
    User findUser = validateUser(user, userId);
    return UserDetailResponse.of(findUser);
  }

  @Transactional
  public void updateMyProfile(User user, Long userId, UserUpdateRequest request) {
    User findUser = validateUser(user, userId);

    String newUsername = request.getUsername();
    if (isExistedUsername(newUsername)) {
      throw new UserCustomException(UserExceptionCode.CONFLICT_USERNAME);
    }

    List<UserSkill> userSkills = request.getSkillList().stream()
        .map(userSkill -> UserSkill.valueOf(userSkill))
        .toList();

    List<Skill> skills = userSkills.stream()
        .map(userSkill -> Skill.builder()
        .userSkill(userSkill)
        .user(user)
        .build())
        .toList();

    findUser.updateUsernameAndSkill(newUsername, skills);
  }

  @Transactional
  public void deleteMyProfile(User user, Long userId) {
    User findUser = validateUser(user, userId);
    findUser.deleteUser();
  }

  public User validateUser(User authUser, Long userId) {
    //OSIV 끄고 테스트 필요 user와 findUser
    if (authUser.getId() != userId) {
      throw new UserCustomException(UserExceptionCode.BAD_REQUEST_USER_ID);
    }

    return getUserById(userId);
  }

  public User getUserById(Long userId) {
    return userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(
        () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  public User getOptUserByOauthId(String oauthId) {
    return userRepository.findByOauthIdAndIsDeletedFalse(oauthId).orElseThrow(
        () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  public User getUserByUsername(String nickname){
    return userRepository.findByUsernameAndIsDeletedFalse(nickname).orElseThrow(
            () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  public boolean isExistedUsername(String nickname){
    return userRepository.existsByUsername(nickname);
  }
}