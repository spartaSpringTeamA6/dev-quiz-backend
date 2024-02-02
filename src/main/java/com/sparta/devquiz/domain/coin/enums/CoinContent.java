package com.sparta.devquiz.domain.coin.enums;

import com.sparta.devquiz.domain.coin.exception.CoinCustomException;
import com.sparta.devquiz.domain.coin.exception.CoinExceptionCode;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CoinContent {

    // 문제
    FIRST("SAVE", "FIRST", 20),
    CORRECT("SAVE","CORRECT", 10),
    FAIL("SAVE","FAIL", 5),
    PASS("SAVE","PASS", 0),
    USE("USE","USE", 0),


    // 아이템
    ITEM_GRASS_PICKUP("USE", "GRASS_PICKUP", 35),
    ITEM_SOMETHING("USE", "UNKNOWN_ITEM", 55);

    private String status;
    private String content;
    private Integer coin;


    public static CoinContent matchingQuizStatus(UserQuizStatus status) {
        return switch (status) {
            case PASS -> PASS;
            case CORRECT -> CORRECT;
            case FAIL -> FAIL;
            default -> throw new CoinCustomException(CoinExceptionCode.BAD_REQUEST_INVALID_QUIZ_STATUS);
        };
    }
}