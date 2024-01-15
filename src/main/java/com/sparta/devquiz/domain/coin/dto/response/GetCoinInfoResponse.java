package com.sparta.devquiz.domain.coin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "보유 코인 조회 응답 dto")
public class GetCoinInfoResponse {

    @Schema(description = "보유 코인", defaultValue = "0")
    private int coins;

    public static GetCoinInfoResponse of(int coins) {
        return GetCoinInfoResponse.builder()
                .coins(coins)
                .build();
    }
}
