package com.sparta.devquiz.domain.user.controller;

import com.sparta.devquiz.domain.user.dto.request.UserSkillsUpdateRequest;
import com.sparta.devquiz.domain.user.dto.request.UsernameUpdateRequest;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.response.UserResponseCode;
import com.sparta.devquiz.domain.user.service.command.UserService;
import com.sparta.devquiz.global.annotation.AuthUser;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}")
@Tag(name = "1-1. User API", description = "User 관련 API 입니다.")
public class UserController {

  private final UserService userService;

  @Operation(operationId = "USER-001", summary = "내 이름 수정")
  @PatchMapping("/username")
  public ResponseEntity<CommonResponseDto> updateMyUsername(
      @AuthUser User authUser, @PathVariable Long userId,
      @RequestBody @Valid UsernameUpdateRequest request)
  {
    userService.updateMyUsername(authUser, userId, request);
    return ResponseEntity.status(UserResponseCode.UPDATE_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.UPDATE_MY_INFO));
  }

  @Operation(operationId = "USER-0016", summary = "내 스킬 수정")
  @PatchMapping("/skills")
  public ResponseEntity<CommonResponseDto> updateMySkills(
      @AuthUser User authUser, @PathVariable Long userId,
      @RequestBody @Valid UserSkillsUpdateRequest request)
  {
    userService.updateMySkills(authUser, userId, request);
    return ResponseEntity.status(UserResponseCode.UPDATE_MY_INFO.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.UPDATE_MY_INFO));
  }

  @Operation(operationId = "USER-002", summary = "회원탈퇴")
  @DeleteMapping
  public ResponseEntity<CommonResponseDto> deleteMyProfile(@AuthUser User authUser, @PathVariable Long userId) {
    userService.deleteMyProfile(authUser, userId);
    return ResponseEntity.status(UserResponseCode.DELETE_USER.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.DELETE_USER));
  }

  @Operation(operationId = "USER-003", summary = "그룹 초대 수락")
  @PostMapping("/teams/{teamId}/accept")
  public ResponseEntity<CommonResponseDto> acceptInvitation(@AuthUser User authUser, @PathVariable Long userId, @PathVariable Long teamId) {
    userService.acceptInvitation(authUser, userId, teamId);
    return ResponseEntity.status(UserResponseCode.ACCEPT_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.ACCEPT_TEAM_INVITATION));
  }

  @Operation(operationId = "USER-004", summary = "그룹 초대 거절")
  @DeleteMapping("/teams/{teamId}/reject")
  public ResponseEntity<CommonResponseDto> rejectInvitation(@AuthUser User authUser, @PathVariable Long userId, @PathVariable Long teamId) {
    userService.rejectInvitation(authUser, userId, teamId);
    return ResponseEntity.status(UserResponseCode.REJECT_TEAM_INVITATION.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.REJECT_TEAM_INVITATION));
  }
}