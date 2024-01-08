package com.sparta.devquiz.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum UserRole {

    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
