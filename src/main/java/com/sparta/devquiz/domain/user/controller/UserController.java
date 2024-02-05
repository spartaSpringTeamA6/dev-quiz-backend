package com.sparta.devquiz.domain.user.controller;

import com.sparta.devquiz.domain.quiz.dto.response.QuizSolvedGrassResponse;
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
import com.sparta.devquiz.domain.user.response.UserResponseCode;
import com.sparta.devquiz.domain.user.service.UserService;
import com.sparta.devquiz.global.annotation.AuthUser;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "1-1. User API", description = "User 관련 API 입니다.")
public class UserController {

  private final UserService userService;

  @Operation(operationId = "USER-001", summary = "내 이름 수정")
  @PatchMapping("/{userId}/username")
  public ResponseEntity<CommonResponseDto> updateMyUsername(
      @AuthUser User authUser, @PathVariable Long userId,
      @RequestBody @Valid UsernameUpdateRequest request)
  {
    userService.updateMyUsername(authUser, userId, request);
    return ResponseEntity.status(UserResponseCode.UPDATE_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.UPDATE_MY_INFO));
  }

  @Operation(operationId = "USER-002", summary = "내 스킬 수정")
  @PatchMapping("/{userId}/skills")
  public ResponseEntity<CommonResponseDto> updateMySkills(
      @AuthUser User authUser, @PathVariable Long userId,
      @RequestBody @Valid UserSkillsUpdateRequest request)
  {
    userService.updateMySkills(authUser, userId, request);
    return ResponseEntity.status(UserResponseCode.UPDATE_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.UPDATE_MY_INFO));
  }

  @Operation(operationId = "USER-003", summary = "회원탈퇴")
  @DeleteMapping("/{userId}")
  public ResponseEntity<CommonResponseDto> deleteMyProfile(@AuthUser User authUser, @PathVariable Long userId) {
    userService.deleteMyProfile(authUser, userId);
    return ResponseEntity.status(UserResponseCode.DELETE_USER.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.DELETE_USER));
  }

  @Operation(operationId = "USER-004", summary = "그룹 초대 수락")
  @PostMapping("/{userId}/teams/{teamId}/accept")
  public ResponseEntity<CommonResponseDto> acceptInvitation(@AuthUser User authUser, @PathVariable Long userId, @PathVariable Long teamId) {
    userService.acceptInvitation(authUser, userId, teamId);
    return ResponseEntity.status(UserResponseCode.ACCEPT_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.ACCEPT_TEAM_INVITATION));
  }

  @Operation(operationId = "USER-005", summary = "그룹 초대 거절")
  @DeleteMapping("/{userId}/teams/{teamId}/reject")
  public ResponseEntity<CommonResponseDto> rejectInvitation(@AuthUser User authUser, @PathVariable Long userId, @PathVariable Long teamId) {
    userService.rejectInvitation(authUser, userId, teamId);
    return ResponseEntity.status(UserResponseCode.REJECT_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.REJECT_TEAM_INVITATION));
  }

  @Operation(operationId = "USER-006", summary = "내 정보 조회")
  @GetMapping("mypage")
  public ResponseEntity<CommonResponseDto> getMyProfile(@AuthUser User authUser) {
    UserDetailResponse result = userService.getMyProfile(authUser);
    return ResponseEntity.status(UserResponseCode.GET_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_INFO, result));
  }

  @Operation(operationId = "USER-007", summary = "사용자 정보 조회")
  @GetMapping("/{userId}")
  public ResponseEntity<CommonResponseDto> getUserProfile(@PathVariable Long userId) {
    UserDetailResponse result = userService.getUserProfile(userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_INFO, result));
  }

  @Operation(operationId = "USER-008", summary = "내 스킬 조회")
  @GetMapping("/{userId}/skills")
  public ResponseEntity<CommonResponseDto> getMySkills(@AuthUser User authUser, @PathVariable Long userId) {
    UserSkillResponse result = userService.getMySkills(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_SKILL.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_SKILL, result));
  }

  @Operation(operationId = "USER-009",summary = "내가 속한 그룹 조회")
  @GetMapping("/{userId}/teams")
  public ResponseEntity<CommonResponseDto> getMyGroups(@AuthUser User authUser, @PathVariable Long userId) {
    UserTeamsResponse result = userService.getMyTeams(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_TEAM.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_TEAM, result));
  }

  @Operation(operationId = "USER-010", summary = "내가 받은 초대 조회")
  @GetMapping("/{userId}/teams/invitations")
  public ResponseEntity<CommonResponseDto> getMyInvitations(@AuthUser User authUser, @PathVariable Long userId) {
    UserInvitationsResponse result = userService.getMyInvitations(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_TEAM_INVITATION, result));
  }

  @Operation(operationId = "USER-011", summary = "내가 작성한 보드 조회")
  @GetMapping("/{userId}/boards")
  public ResponseEntity<CommonResponseDto> getMyBoards(@AuthUser User authUser, @PathVariable Long userId) {
    UserBoardsResponse result = userService.getMyBoards(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_BOARD.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_BOARD, result));
  }

  @Operation(operationId = "USER-012", summary = "내가 작성한 댓글 조회")
  @GetMapping("/{userId}/comments")
  public ResponseEntity<CommonResponseDto> getMyComments(@AuthUser User authUser, @PathVariable Long userId) {
    UserCommentsResponse result = userService.getMyComments(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_COMMENT.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_COMMENT, result));
  }

  @Operation(operationId = "USER-013", summary = "내가 시도한 문제 조회")
  @GetMapping("/{userId}/quizzes")
  public ResponseEntity<CommonResponseDto> getMyQuizzes(@AuthUser User authUser, @PathVariable Long userId) {
    UserQuizzesResponse result = userService.getMyQuizzes(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_QUIZ.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_QUIZ, result));
  }

  @Operation(operationId = "USER-014", summary = "내가 맞은 문제 조회")
  @GetMapping("/{userId}/quizzes/correct")
  public ResponseEntity<CommonResponseDto> getMyCorrectQuizzes(@AuthUser User authUser, @PathVariable Long userId) {
    UserQuizzesResponse result = userService.getMyCorrectQuizzes(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_CORRECT_QUIZ.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_CORRECT_QUIZ, result));
  }

  @Operation(operationId = "USER-015", summary = "내가 틀린 문제 조회")
  @GetMapping("/{userId}/quizzes/fail")
  public ResponseEntity<CommonResponseDto> getMyFailQuizzes(@AuthUser User authUser, @PathVariable Long userId) {
    UserQuizzesResponse result = userService.getMyFailQuizzes(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_FAILED_QUIZ.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_FAILED_QUIZ, result));
  }

  @Operation(operationId = "USER-016", summary = "내가 모르는 문제 조회")
  @GetMapping("/{userId}/quizzes/pass")
  public ResponseEntity<CommonResponseDto> getMyPassQuizzes(@AuthUser User authUser, @PathVariable Long userId) {
    UserQuizzesResponse result = userService.getMyPassQuizzes(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_PASSED_QUIZ.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_PASSED_QUIZ, result));
  }

  @Operation(operationId="USER-017", summary = "내 잔디 조회")
  @GetMapping("/{userId}/grasses")
  public ResponseEntity<CommonResponseDto> getMyGrasses(@AuthUser User authUser, @PathVariable Long userId) {
    List<QuizSolvedGrassResponse> result = userService.getMyGrasses(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_GRASSES.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_GRASSES, result));
  }
}