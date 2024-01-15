package com.sparta.devquiz.domain.coin.dto.request;

import com.sparta.devquiz.domain.coin.enums.CoinContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "코인 저장 요청 dto")
public class SaveCoinRequest {

    @Schema(description = "저장 코인")
    private int coins;
    @Schema(description = "저장 코인 용도", defaultValue = "CORRECT")
    private CoinContent coinContent;
}
