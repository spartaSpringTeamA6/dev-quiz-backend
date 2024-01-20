package com.sparta.devquiz.domain.user.service.command;

import com.sparta.devquiz.domain.team.service.TeamUserService;
import com.sparta.devquiz.domain.user.dto.request.UserUpdateRequest;
import com.sparta.devquiz.domain.user.entity.Skill;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.UserSkill;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final TeamUserService teamUserService;

  public void updateMyProfile(User authUser, Long userId, UserUpdateRequest request) {
    User findUser = validateUser(authUser, userId);

    String newUsername = request.getUsername();
    if (!findUser.getUsername().equals(newUsername) && isExistedUsername(newUsername)) {
      throw new UserCustomException(UserExceptionCode.CONFLICT_USERNAME);
    }

    LinkedHashSet<String> skillSet = new LinkedHashSet<>(request.getSkillList());
    Set<UserSkill> userSkills = skillSet.stream()
        .map(UserSkill::valueOf)
        .collect(Collectors.toUnmodifiableSet());

    Set<Skill> skills = userSkills.stream()
        .map(userSkill -> Skill
            .builder()
            .userSkill(userSkill)
            .user(findUser)
            .build())
        .collect(Collectors.toUnmodifiableSet());

    findUser.updateUsernameAndSkill(newUsername, skills);
  }

  public void deleteMyProfile(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    findUser.deleteUser();
    findUser.getTeamUserList()
        .forEach(teamUser ->
            teamUserService.deleteTeamUser(teamUser.getTeam().getId(), teamUser.getUser().getId()));
  }

  public void acceptInvitation(User authUser, Long userId, Long teamId) {
    User findUser = validateUser(authUser, userId);
    teamUserService.acceptInvitation(teamId, findUser.getId());
  }

  public void rejectInvitation(User authUser, Long userId, Long teamId) {
    User findUser = validateUser(authUser, userId);
    teamUserService.rejectInvitation(teamId, findUser.getId());
  }

  public User validateUser(User authUser, Long userId) {
    //OSIV 끄고 테스트 필요 user와 findUser == 비교가 안 된다.
    if (!authUser.getId().equals(userId)) {
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

  public User getUserByUsername(String username){
    return userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(
            () -> new UserCustomException(UserExceptionCode.NOT_FOUND_USER)
    );
  }

  public boolean isExistedUsername(String username){
    return userRepository.existsByUsername(username);
  }
}