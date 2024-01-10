package com.sparta.devquiz.domain.coin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoinContent {

    FIRST("FIRST", 20),
    CORRECT("CORRECT", 10),
    FAIL("FAIL", 5),
    PASS("PASS", 0);

    private final String status;
    private final Integer coins;

}