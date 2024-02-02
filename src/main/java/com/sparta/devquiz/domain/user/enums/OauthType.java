package com.sparta.devquiz.domain.user.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OauthType {
  GOOGLE("GOOGLE"),
  GITHUB("GITHUB");

  private final String type;
}
