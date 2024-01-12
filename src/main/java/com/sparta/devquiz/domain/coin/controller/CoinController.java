package com.sparta.devquiz.domain.coin.controller;

import com.sparta.devquiz.domain.coin.dto.request.SaveCoinRequest;
import com.sparta.devquiz.domain.coin.dto.request.UseCoinRequest;
import com.sparta.devquiz.domain.coin.dto.response.GetCoinInfoResponse;
import com.sparta.devquiz.domain.coin.dto.response.SaveCoinResponse;
import com.sparta.devquiz.domain.coin.dto.response.UseCoinResponse;
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


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "COIN-001", summary = "코인 적립")
    @PostMapping("/save")
    public ResponseEntity<CommonResponseDto<SaveCoinResponse>> saveCoin(@PathVariable Long userId, @RequestBody SaveCoinRequest saveCoinRequest,
                                                                         @AuthUser User authUser) {
        SaveCoinResponse saveCoinResponse = coinService.saveCoin(userId, saveCoinRequest, authUser);

        return ResponseEntity.status(CoinResponseCode.CREATED_SAVE_COIN.getHttpStatus())
                .body(CommonResponseDto.of(CoinResponseCode.CREATED_SAVE_COIN, saveCoinResponse));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "COIN-002", summary = "코인 사용")
    @PostMapping("/use")
    public ResponseEntity<CommonResponseDto<UseCoinResponse>> useCoin(@PathVariable Long userId, @RequestBody UseCoinRequest useCoinRequest,
                                                                      @AuthUser User authUser) {

        UseCoinResponse useCoinResponse = coinService.useCoin(userId, useCoinRequest, authUser);

        return ResponseEntity.status(CoinResponseCode.NO_CONTENT_USE_COIN.getHttpStatus())
                .body(CommonResponseDto.of(CoinResponseCode.NO_CONTENT_USE_COIN, useCoinResponse));
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "COIN-003", summary = "코인 조회")
    @GetMapping

    public ResponseEntity<CommonResponseDto<GetCoinInfoResponse>> getCoinInfo(@PathVariable Long userId, @AuthUser User authUser) {

        GetCoinInfoResponse getCoinInfoResponse = coinService.getCoinInfo(userId, authUser);

        return ResponseEntity.status(CoinResponseCode.OK_GET_MY_COIN.getHttpStatus())
                .body(CommonResponseDto.of(CoinResponseCode.OK_GET_MY_COIN, getCoinInfoResponse));
    }

}
