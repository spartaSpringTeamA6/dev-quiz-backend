package com.sparta.devquiz.domain.coin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoinStatus {

    SAVE("SAVE"),
    USE("USE");

    private final String status;
}
