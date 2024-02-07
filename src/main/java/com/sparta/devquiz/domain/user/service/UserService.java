package com.sparta.devquiz.domain.user.service;

import com.sparta.devquiz.domain.board.dto.response.BoardDetailsResponse;
import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.board.repository.BoardRepository;
import com.sparta.devquiz.domain.comment.dto.response.CommentInfoResponse;
import com.sparta.devquiz.domain.comment.entity.Comment;
import com.sparta.devquiz.domain.comment.repository.CommentRepository;
import com.sparta.devquiz.domain.quiz.dto.response.QuizGetByUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizSolvedGrassResponse;
import com.sparta.devquiz.domain.quiz.repository.QuizUserRepository;
import com.sparta.devquiz.domain.skill.repository.SkillRepository;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.repository.TeamUserRepository;
import com.sparta.devquiz.domain.skill.entity.Skill;
import com.sparta.devquiz.domain.user.dto.request.UserSkillsUpdateRequest;
import com.sparta.devquiz.domain.user.dto.request.UsernameUpdateRequest;
import com.sparta.devquiz.domain.user.dto.response.UserBoardsResponse;
import com.sparta.devquiz.domain.user.dto.response.UserCommentsResponse;
import com.sparta.devquiz.domain.user.dto.response.UserDetailResponse;
import com.sparta.devquiz.domain.user.dto.response.UserInvitationsResponse;
import com.sparta.devquiz.domain.user.dto.response.UserQuizzesResponse;
import com.sparta.devquiz.domain.user.dto.response.UserSkillResponse;
import com.sparta.devquiz.domain.user.dto.response.UserTeamsResponse;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.skill.enums.UserSkill;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final CommentRepository commentRepository;
  private final SkillRepository skillRepository;
  private final QuizUserRepository quizUserRepository;
  private final TeamUserRepository teamUserRepository;


  @Transactional
  public void updateMyUsername(User authUser, Long userId, UsernameUpdateRequest request) {
    User findUser = validateUser(authUser, userId);

    String newUsername = request.getUsername();
    if (!findUser.getUsername().equals(newUsername) && userRepository.existsByUsername(newUsername)) {
      throw new UserCustomException(UserExceptionCode.CONFLICT_USERNAME);
    }

    findUser.updateUsername(newUsername);
  }

  @Transactional
  public void updateMySkills(User authUser, Long userId, UserSkillsUpdateRequest request) {
    User findUser = validateUser(authUser, userId);

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

    findUser.updateSkills(skills);
  }

  @Transactional
  public void deleteMyProfile(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    findUser.deleteUser();
    teamUserRepository.deleteAll(findUser.getTeamUserList());
  }

  public UserDetailResponse getMyProfile(User authUser) {
    User findUser = userRepository.findByIdOrElseThrow(authUser.getId());
    return UserDetailResponse.of(findUser);
  }

  public UserDetailResponse getUserProfile(Long userId) {
    User findUser = userRepository.findByIdOrElseThrow(userId);
    return UserDetailResponse.of(findUser);
  }

  public UserSkillResponse getMySkills(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<Skill> skillList = skillRepository.findAllByUserId(userId);
    return UserSkillResponse.of(findUser, skillList);
  }

  public UserTeamsResponse getMyTeams(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<TeamUser> findTeamUserList = teamUserRepository.getTeamUserByUser(findUser.getId());
    return UserTeamsResponse.of(findUser, findTeamUserList);
  }

  public UserInvitationsResponse getMyInvitations(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<TeamUser> findTeamUserList = teamUserRepository.getTeamUserByUserAndWait(findUser.getId());
    return UserInvitationsResponse.of(findUser, findTeamUserList);
  }

  public UserBoardsResponse getMyBoards(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<Board> boards = boardRepository.findAllByUserIdAndIsDeletedFalse(userId);
    List<BoardDetailsResponse> boardDetailsList = BoardDetailsResponse.of(boards);
    return UserBoardsResponse.of(findUser, boardDetailsList);
  }

  public UserCommentsResponse getMyComments(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<Comment> comments = commentRepository.findAllByUserIdAndIsDeletedFalse(userId);
    List<CommentInfoResponse> commentDetailsList = CommentInfoResponse.of(comments);
    return UserCommentsResponse.of(findUser, commentDetailsList);
  }

  public UserQuizzesResponse getMyQuizzes(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<QuizGetByUserResponse> quizList = quizUserRepository.getAllQuizzesForUser(findUser);
    return UserQuizzesResponse.of(findUser, quizList);
  }

  public UserQuizzesResponse getMyCorrectQuizzes(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<QuizGetByUserResponse> correctQuizList = quizUserRepository.getCorrectQuizzesForUser(findUser);
    return UserQuizzesResponse.of(findUser, correctQuizList);
  }

  public UserQuizzesResponse getMyFailQuizzes(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<QuizGetByUserResponse> failQuizList = quizUserRepository.getFailQuizzesForUser(findUser);
    return UserQuizzesResponse.of(findUser, failQuizList);
  }

  public UserQuizzesResponse getMyPassQuizzes(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<QuizGetByUserResponse> passQuizList = quizUserRepository.getPassQuizzesForUser(findUser);
    return UserQuizzesResponse.of(findUser, passQuizList);
  }

  public List<QuizSolvedGrassResponse> getMyGrasses(User authUser, Long userId) {
    validateUser(authUser,userId);
    return quizUserRepository.getSolvedGrassByUser(authUser);
  }

  private User validateUser(User authUser, Long userId) {
    if (!authUser.getId().equals(userId)) {
      throw new UserCustomException(UserExceptionCode.BAD_REQUEST_USER_ID);
    }
    return userRepository.findByIdOrElseThrow(userId);
  }

}