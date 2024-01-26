package com.sparta.devquiz.domain.coin.controller;

import com.sparta.devquiz.domain.coin.dto.request.CoinSaveRequest;
import com.sparta.devquiz.domain.coin.dto.request.CoinUseRequest;
import com.sparta.devquiz.domain.coin.dto.response.CoinGetInfoResponse;
import com.sparta.devquiz.domain.coin.dto.response.CoinUseResponse;
import com.sparta.devquiz.domain.coin.enums.CoinContent;
import com.sparta.devquiz.domain.coin.response.CoinResponseCode;
import com.sparta.devquiz.domain.coin.service.CoinService;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.annotation.AuthUser;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/coins")
@RequiredArgsConstructor
@Tag(name = "3. Coin API", description = "Coin 관련 API 입니다.")
public class CoinController {

    private final CoinService coinService;


//    @SecurityRequirement(name = "Bearer Authentication")
//    @Operation(operationId = "COIN-001", summary = "코인 적립   테스트용")
//    @PostMapping("/save")
//    public ResponseEntity<CommonResponseDto> saveCoin(@PathVariable Long userId, @RequestBody CoinContent coinContent,
//                                                      @AuthUser User authUser) {
//        coinService.saveCoin(userId, coinContent, authUser);
//
//        return ResponseEntity.status(CoinResponseCode.CREATED_SAVE_COIN.getHttpStatus())
//                .body(CommonResponseDto.of(CoinResponseCode.CREATED_SAVE_COIN));
//    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "COIN-002", summary = "코인 사용")
    @PostMapping("/use")
    public ResponseEntity<CommonResponseDto<CoinUseResponse>> useCoin(@PathVariable Long userId, @RequestBody CoinUseRequest coinUseRequest,
                                                                      @AuthUser User authUser) {

        CoinUseResponse coinUseResponse = coinService.useCoin(userId, coinUseRequest, authUser);

        return ResponseEntity.status(CoinResponseCode.OK_USE_COIN.getHttpStatus())
                .body(CommonResponseDto.of(CoinResponseCode.OK_USE_COIN, coinUseResponse));
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "COIN-003", summary = "코인 조회")
    @GetMapping

    public ResponseEntity<CommonResponseDto<CoinGetInfoResponse>> getCoinInfo(@PathVariable Long userId, @AuthUser User authUser) {

        CoinGetInfoResponse coinGetInfoResponse = coinService.getCoinInfo(userId, authUser);

        return ResponseEntity.status(CoinResponseCode.OK_GET_MY_COIN.getHttpStatus())
                .body(CommonResponseDto.of(CoinResponseCode.OK_GET_MY_COIN, coinGetInfoResponse));
    }

}
