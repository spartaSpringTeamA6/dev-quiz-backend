package com.sparta.devquiz.domain.coin.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UseCoinRequest {

    private final Long coins;

    private final Long payment;

}
