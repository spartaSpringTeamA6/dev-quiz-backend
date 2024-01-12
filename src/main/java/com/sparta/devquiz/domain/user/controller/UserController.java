package com.sparta.devquiz.domain.user.controller;

import com.sparta.devquiz.domain.user.service.UserService;
import com.sparta.devquiz.global.response.CommonResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/logout")
  public ResponseEntity<CommonResponseDto> logout(HttpServletRequest request, HttpServletResponse response) {
    SecurityContextHolder.clearContext();
    return null;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<CommonResponseDto> getMyProfile() {
    return null;
  }

  @PatchMapping("/{userId}")
  public ResponseEntity<CommonResponseDto> updateMyProfile() {
    return null;
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<CommonResponseDto> deleteUser() {
    return null;
  }

  @GetMapping("/{userId}/boards")
  public ResponseEntity<CommonResponseDto> getMyBoards() {
    return null;
  }

  @GetMapping("/{userId}/comments")
  public ResponseEntity<CommonResponseDto> getMyComments() {
    return null;
  }





}
