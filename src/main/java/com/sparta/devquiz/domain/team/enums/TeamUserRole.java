package com.sparta.devquiz.domain.team.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TeamUserRole {
    USER("TEAM_USER"),
    ADMIN("TEAM_ADMIN");

    private final String role;
}