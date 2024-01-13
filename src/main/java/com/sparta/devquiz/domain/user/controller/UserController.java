package com.sparta.devquiz.domain.user.controller;

import com.sparta.devquiz.domain.user.dto.request.UserUpdateRequest;
import com.sparta.devquiz.domain.user.dto.response.UserDetailResponse;
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
}