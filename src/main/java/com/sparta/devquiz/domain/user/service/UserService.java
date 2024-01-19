package com.sparta.devquiz.domain.user.service;

import com.sparta.devquiz.domain.board.dto.response.BoardDetailsResponse;
import com.sparta.devquiz.domain.board.service.BoardService;
import com.sparta.devquiz.domain.comment.dto.response.CommentInfoResponse;
import com.sparta.devquiz.domain.comment.service.CommentService;
import com.sparta.devquiz.domain.mypage.service.UserTeamUserService;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.user.dto.request.UserUpdateRequest;
import com.sparta.devquiz.domain.user.dto.response.UserBoardsResponse;
import com.sparta.devquiz.domain.user.dto.response.UserCommentsResponse;
import com.sparta.devquiz.domain.user.dto.response.UserDetailResponse;
import com.sparta.devquiz.domain.user.dto.response.UserInvitationsResponse;
import com.sparta.devquiz.domain.user.dto.response.UserSkillResponse;
import com.sparta.devquiz.domain.user.dto.response.UserTeamsResponse;
import com.sparta.devquiz.domain.user.entity.Skill;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.UserSkill;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import com.sparta.devquiz.domain.user.repository.SkillRepository;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final BoardService boardService;
  private final CommentService commentService;
  private final UserRepository userRepository;
  private final SkillRepository skillRepository;
  private final UserTeamUserService userTeamUserService;
  public UserDetailResponse getMyProfile(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    return UserDetailResponse.of(findUser);
  }

  @Transactional
  public void updateMyProfile(User authUser, Long userId, UserUpdateRequest request) {
    User findUser = validateUser(authUser, userId);

    String newUsername = request.getUsername();
    if (!findUser.getUsername().equals(newUsername) && isExistedUsername(newUsername)) {
      throw new UserCustomException(UserExceptionCode.CONFLICT_USERNAME);
    }

    List<UserSkill> userSkills = request.getSkillList().stream()
        .map(UserSkill::valueOf)
        .toList();

    List<Skill> skills = userSkills.stream()
        .map(userSkill -> Skill
            .builder()
            .userSkill(userSkill)
            .user(findUser)
            .build())
        .toList();

    findUser.updateUsernameAndSkill(newUsername, skills);
  }

  @Transactional
  public void deleteMyProfile(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    findUser.deleteUser();
    findUser.getTeamUserList()
        .forEach(teamUser ->
            userTeamUserService.deleteTeamUser(teamUser.getTeam().getId(), teamUser.getUser().getId()));
  }

  public UserSkillResponse getMySkills(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<Skill> skillList = skillRepository.findAllByUserId(userId);
    return UserSkillResponse.of(findUser, skillList);
  }

  public UserTeamsResponse getMyTeams(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<TeamUser> findTeamUserList = userTeamUserService.getTeamUserByUser(findUser.getId());
    return UserTeamsResponse.of(findUser, findTeamUserList);
  }

  public UserInvitationsResponse getMyInvitations(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<TeamUser> findTeamUserList = userTeamUserService.getTeamUserByUserAndWait(findUser.getId());
    return UserInvitationsResponse.of(findUser, findTeamUserList);
  }

  @Transactional
  public void acceptInvitation(User authUser, Long userId, Long teamId) {
    User findUser = validateUser(authUser, userId);
    userTeamUserService.acceptInvitation(teamId, findUser.getId());
  }

  @Transactional
  public void rejectInvitation(User authUser, Long userId, Long teamId) {
    User findUser = validateUser(authUser, userId);
    userTeamUserService.rejectInvitation(teamId, findUser.getId());
  }

  public UserBoardsResponse getMyBoards(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<BoardDetailsResponse> boardDetailsList = boardService.getBoardListByUserId(userId);
    return UserBoardsResponse.of(findUser, boardDetailsList);
  }

  public UserCommentsResponse getMyComments(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<CommentInfoResponse> commentDEtailsList = commentService.getCommentListByUserId(userId);
    return UserCommentsResponse.of(findUser, commentDEtailsList);
  }

  public User validateUser(User authUser, Long userId) {
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