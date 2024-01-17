package com.sparta.devquiz.global.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenSet {
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public TokenSet(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}