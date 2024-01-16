package com.sparta.devquiz.domain.coin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CoinContent {

    // 문제
    FIRST("SAVE", "FIRST", () -> 20),
    CORRECT("SAVE","CORRECT", () -> 10),
    FAIL("SAVE","FAIL", () -> 5),
    PASS("SAVE","PASS", () -> 0),
    USE("USE","USE", () -> 0),


    // 아이템
    ITEM_CAT("USE", "CAT", () -> 25),
    ITEM_DOG("USE", "DOG", () -> 45);

    private String status;
    private String content;
    private Supplier<Integer> coinSupplier;

}