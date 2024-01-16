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
@Schema(description = "코인 사용 응답 dto")
public class CoinUseResponse {

    @Schema(description = "코인 사용 내역", defaultValue = "0")
    private int coins;

    public static CoinUseResponse of(int coins) {
        return CoinUseResponse.builder()
                .coins(coins)
                .build();
    }
}
