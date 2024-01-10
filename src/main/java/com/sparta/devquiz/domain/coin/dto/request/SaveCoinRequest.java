package com.sparta.devquiz.domain.coin.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveCoinRequest {

    private final Long coin;

}
