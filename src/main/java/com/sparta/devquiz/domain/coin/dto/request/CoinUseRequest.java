package com.sparta.devquiz.domain.coin.dto.request;

import com.sparta.devquiz.domain.coin.enums.CoinContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "코인 사용 요청 dto")
public class CoinUseRequest {

    @Schema(description = "보유 코인", defaultValue = "0")
    private int coins;
    @Schema(description = "코인 사용 용도", defaultValue = "USE")
    private CoinContent coinContent;

}
