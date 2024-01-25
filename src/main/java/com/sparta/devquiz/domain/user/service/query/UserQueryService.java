package com.sparta.devquiz.domain.user.service.query;

import com.sparta.devquiz.domain.board.dto.response.BoardDetailsResponse;
import com.sparta.devquiz.domain.board.service.BoardService;
import com.sparta.devquiz.domain.comment.dto.response.CommentInfoResponse;
import com.sparta.devquiz.domain.comment.service.CommentService;
import com.sparta.devquiz.domain.quiz.dto.response.QuizGetByUserResponse;
import com.sparta.devquiz.domain.quiz.service.QuizService;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.service.TeamUserService;
import com.sparta.devquiz.domain.user.dto.response.UserBoardsResponse;
import com.sparta.devquiz.domain.user.dto.response.UserCommentsResponse;
import com.sparta.devquiz.domain.user.dto.response.UserDetailResponse;
import com.sparta.devquiz.domain.user.dto.response.UserInvitationsResponse;
import com.sparta.devquiz.domain.user.dto.response.UserQuizzesResponse;
import com.sparta.devquiz.domain.user.dto.response.UserSkillResponse;
import com.sparta.devquiz.domain.user.dto.response.UserTeamsResponse;
import com.sparta.devquiz.domain.user.entity.Skill;
import com.sparta.devquiz.domain.user.entity.User;
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
public class UserQueryService {

  private final BoardService boardService;
  private final CommentService commentService;
  private final UserRepository userRepository;
  private final SkillRepository skillRepository;
  private final QuizService quizService;
  private final TeamUserService teamUserService;

  public UserDetailResponse getMyProfile(User authUser) {
    User findUser = getUserById(authUser.getId());
    return UserDetailResponse.of(findUser);
  }

  public UserSkillResponse getMySkills(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<Skill> skillList = skillRepository.findAllByUserId(userId);
    return UserSkillResponse.of(findUser, skillList);
  }

  public UserTeamsResponse getMyTeams(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<TeamUser> findTeamUserList = teamUserService.getTeamUserByUser(findUser.getId());
    return UserTeamsResponse.of(findUser, findTeamUserList);
  }

  public UserInvitationsResponse getMyInvitations(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<TeamUser> findTeamUserList = teamUserService.getTeamUserByUserAndWait(findUser.getId());
    return UserInvitationsResponse.of(findUser, findTeamUserList);
  }

  public UserBoardsResponse getMyBoards(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<BoardDetailsResponse> boardDetailsList = boardService.getBoardListByUserId(userId);
    return UserBoardsResponse.of(findUser, boardDetailsList);
  }

  public UserCommentsResponse getMyComments(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<CommentInfoResponse> commentDetailsList = commentService.getCommentListByUserId(userId);
    return UserCommentsResponse.of(findUser, commentDetailsList);
  }

  public UserQuizzesResponse getMyQuizzes(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<QuizGetByUserResponse> quizList = quizService.getAllQuizzesForUser(findUser);
    return UserQuizzesResponse.of(findUser, quizList);
  }

  public UserQuizzesResponse getMyCorrectQuizzes(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<QuizGetByUserResponse> correctQuizList = quizService.getCorrectQuizzesForUser(findUser);
    return UserQuizzesResponse.of(findUser, correctQuizList);
  }

  public UserQuizzesResponse getMyFailQuizzes(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<QuizGetByUserResponse> failQuizList = quizService.getFailQuizzesForUser(findUser);
    return UserQuizzesResponse.of(findUser, failQuizList);
  }

  public UserQuizzesResponse getMyPassQuizzes(User authUser, Long userId) {
    User findUser = validateUser(authUser, userId);
    List<QuizGetByUserResponse> passQuizList = quizService.getPassQuizzesForUser(findUser);
    return UserQuizzesResponse.of(findUser, passQuizList);
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
}