package com.sparta.devquiz.domain.user.controller;

import com.sparta.devquiz.domain.user.dto.request.UserUpdateRequest;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}")
@Tag(name = "1-1. User API", description = "User 관련 API 입니다.")
public class UserController {

  private final UserService userService;

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-001", summary = "내 정보 조회")
  @GetMapping
  public ResponseEntity<CommonResponseDto> getMyProfile(@AuthUser User authUser, @PathVariable Long userId) {
    UserDetailResponse result = userService.getMyProfile(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_INFO, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-002", summary = "내 정보 수정")
  @PutMapping
  public ResponseEntity<CommonResponseDto> updateMyProfile(
      @AuthUser User authUser, @PathVariable Long userId,
      @RequestBody @Valid UserUpdateRequest request)
  {
    userService.updateMyProfile(authUser, userId, request);
    return ResponseEntity.status(UserResponseCode.UPDATE_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.UPDATE_MY_INFO));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-003", summary = "회원탈퇴")
  @DeleteMapping
  public ResponseEntity<CommonResponseDto> deleteMyProfile(@AuthUser User authUser, @PathVariable Long userId) {
    userService.deleteMyProfile(authUser, userId);
    return ResponseEntity.status(UserResponseCode.DELETE_USER.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.DELETE_USER));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-004", summary = "내 스킬 조회")
  @GetMapping("/skills")
  public ResponseEntity<CommonResponseDto> getMySkills(@AuthUser User authUser, @PathVariable Long userId) {
    UserSkillResponse result = userService.getMySkills(authUser, userId);
    return ResponseEntity.status(UserResponseCode.DELETE_USER.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.DELETE_USER, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-005",summary = "내가 속한 그룹 조회")
  @GetMapping("/teams")
  public ResponseEntity<CommonResponseDto> getMyGroups(@AuthUser User authUser, @PathVariable Long userId) {
    UserTeamsResponse result = userService.getMyTeams(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_TEAM.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_TEAM, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-006", summary = "내가 받은 초대 조회")
  @GetMapping("/teams/invitations")
  public ResponseEntity<CommonResponseDto> getMyInvitations(@AuthUser User authUser, @PathVariable Long userId) {
    UserInvitationsResponse result = userService.getMyInvitations(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_TEAM_INVITATION, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-007", summary = "그룹 초대 수락")
  @PostMapping("/teams/{teamId}/accept")
  public ResponseEntity<CommonResponseDto> acceptInvitation(@AuthUser User authUser, @PathVariable Long userId, @PathVariable Long teamId) {
    userService.acceptInvitation(authUser, userId, teamId);
    return ResponseEntity.status(UserResponseCode.ACCEPT_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.ACCEPT_TEAM_INVITATION));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-008", summary = "그룹 초대 거절")
  @DeleteMapping("/teams/{teamId}/reject")
  public ResponseEntity<CommonResponseDto> rejectInvitation(@AuthUser User authUser, @PathVariable Long userId, @PathVariable Long teamId) {
    userService.rejectInvitation(authUser, userId, teamId);
    return ResponseEntity.status(UserResponseCode.REJECT_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.REJECT_TEAM_INVITATION));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-009", summary = "내가 작성한 보드 조회")
  @GetMapping("/boards")
  public ResponseEntity<CommonResponseDto> getMyBoards(@AuthUser User authUser, @PathVariable Long userId) {
    UserBoardsResponse result = userService.getMyBoards(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_BOARD.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_BOARD, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-010", summary = "내가 작성한 댓글 조회")
  @GetMapping("/comments")
  public ResponseEntity<CommonResponseDto> getMyComments(@AuthUser User authUser, @PathVariable Long userId) {
    UserCommentsResponse result = userService.getMyComments(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_COMMENT.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_COMMENT, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-011", summary = "내가 시도한 문제 조회")
  @GetMapping("/quizzes")
  public ResponseEntity<CommonResponseDto> getMyQuizzes(@AuthUser User authUser, @PathVariable Long userId) {
    UserQuizzesResponse result = userService.getMyQuizzes(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_QUIZ.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_QUIZ, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-012", summary = "내가 맞은 문제 조회")
  @GetMapping("/quizzes/correct")
  public ResponseEntity<CommonResponseDto> getMyCorrectQuizzes(@AuthUser User authUser, @PathVariable Long userId) {
    UserQuizzesResponse result = userService.getMyCorrectQuizzes(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_CORRECT_QUIZ.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_CORRECT_QUIZ, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-013", summary = "내가 틀린 문제 조회")
  @GetMapping("/quizzes/fail")
  public ResponseEntity<CommonResponseDto> getMyFailQuizzes(@AuthUser User authUser, @PathVariable Long userId) {
    UserQuizzesResponse result = userService.getMyFailQuizzes(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_FAILED_QUIZ.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_FAILED_QUIZ, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(operationId = "USER-014", summary = "내가 모르는 문제 조회")
  @GetMapping("/quizzes/pass")
  public ResponseEntity<CommonResponseDto> getMyPassQuizzes(@AuthUser User authUser, @PathVariable Long userId) {
    UserQuizzesResponse result = userService.getMyPassQuizzes(authUser, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_PASSED_QUIZ.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_PASSED_QUIZ, result));
  }
}