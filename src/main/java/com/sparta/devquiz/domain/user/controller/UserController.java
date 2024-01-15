package com.sparta.devquiz.domain.user.controller;

import com.sparta.devquiz.domain.user.dto.request.UserUpdateRequest;
import com.sparta.devquiz.domain.user.dto.response.UserDetailResponse;
import com.sparta.devquiz.domain.user.dto.response.UserInvitationsResponse;
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
@Tag(name = "1. User API", description = "User 관련 API 입니다.")
public class UserController {

  private final UserService userService;

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "내 정보 조회")
  @GetMapping
  public ResponseEntity<CommonResponseDto> getMyProfile(@AuthUser User user, @PathVariable Long userId) {
    UserDetailResponse result = userService.getMyProfile(user, userId);
    
    return ResponseEntity.status(UserResponseCode.GET_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_INFO, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "내 정보 수정")
  @PutMapping
  public ResponseEntity<CommonResponseDto> updateMyProfile(
      @AuthUser User user, @PathVariable Long userId,
      @RequestBody @Valid UserUpdateRequest request)
  {
    userService.updateMyProfile(user, userId, request);
    return ResponseEntity.status(UserResponseCode.UPDATE_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.UPDATE_MY_INFO));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "회원탈퇴")
  @DeleteMapping
  public ResponseEntity<CommonResponseDto> deleteMyProfile(@AuthUser User user, @PathVariable Long userId) {
    userService.deleteMyProfile(user, userId);
    return ResponseEntity.status(UserResponseCode.DELETE_USER.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.DELETE_USER));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "내가 속한 그룹 조회")
  @GetMapping("/teams")
  public ResponseEntity<CommonResponseDto> getMyGroups(@AuthUser User user, @PathVariable Long userId) {
    UserTeamsResponse result = userService.getMyTeams(user, userId);
    return ResponseEntity.status(UserResponseCode.GET_MY_TEAM.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_MY_TEAM, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "내가 받은 초대 조회")
  @GetMapping("/teams/invitations")
  public ResponseEntity<CommonResponseDto> getMyInvitations(@AuthUser User user, @PathVariable Long userId) {
    UserInvitationsResponse result = userService.getMyInvitations(user, userId);
    return ResponseEntity.status(UserResponseCode.GET_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.GET_TEAM_INVITATION, result));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "그룹 초대 수락")
  @PostMapping("/teams/{teamId}/accept")
  public ResponseEntity<CommonResponseDto> acceptInvitation(@AuthUser User user, @PathVariable Long userId, @PathVariable Long teamId) {
    userService.acceptInvitation(user, userId, teamId);
    return ResponseEntity.status(UserResponseCode.ACCEPT_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.ACCEPT_TEAM_INVITATION));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "그룹 초대 거절")
  @DeleteMapping("/teams/{teamId}/reject")
  public ResponseEntity<CommonResponseDto> rejectInvitation(@AuthUser User user, @PathVariable Long userId, @PathVariable Long teamId) {
    userService.rejectInvitation(user, userId, teamId);
    return ResponseEntity.status(UserResponseCode.REJECT_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.REJECT_TEAM_INVITATION));
  }
}