package com.sparta.devquiz.domain.coin.dto.request;

import com.sparta.devquiz.domain.coin.enums.CoinContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveCoinRequest {

    private final int coins;
    private final CoinContent coinContent;
}
