package com.sparta.devquiz.domain.user.controller;

import com.sparta.devquiz.domain.user.response.UserResponseCode;
import com.sparta.devquiz.domain.user.service.command.AuthService;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "1-3. Auth API", description = "Auth 관련 API 입니다.")
public class AuthController {

  private final AuthService authService;

  @Operation(operationId = "AUTH-001", summary = "로그아웃")
  @PostMapping("/logout")
  public ResponseEntity<CommonResponseDto> logout(HttpServletRequest request) {
    authService.logout(request);
    return ResponseEntity.status(UserResponseCode.LOGOUT.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.LOGOUT));
  }

  @Operation(operationId = "AUTH-002", summary = "토큰 재발급")
  @PostMapping("/reissue")
  public ResponseEntity<CommonResponseDto> reissue(HttpServletRequest request, HttpServletResponse response) {
    authService.reissue(request, response);
    return ResponseEntity.status(UserResponseCode.REISSUE.getHttpStatus())
        .body(CommonResponseDto.of(UserResponseCode.REISSUE));
  }
}