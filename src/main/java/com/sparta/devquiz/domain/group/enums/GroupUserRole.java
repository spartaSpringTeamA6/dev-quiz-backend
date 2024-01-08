package com.sparta.devquiz.domain.group.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupUserRole {
    USER("GROUP_USER"),
    ADMIN("GROUP_ADMIN");

    private final String role;
}
