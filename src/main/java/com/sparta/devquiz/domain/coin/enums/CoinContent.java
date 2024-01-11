package com.sparta.devquiz.domain.coin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public enum CoinContent {

    FIRST("SAVE", "FIRST", () -> 20L),
    CORRECT("SAVE","CORRECT", () -> 10L),
    FAIL("SAVE","FAIL", () -> 5L),
    PASS("SAVE","PASS", () -> 0L),
    USE("USE","USE", () -> 0L);

    private final String status;
    private final String content;
    private final Supplier<Long> coinSupplier;

}