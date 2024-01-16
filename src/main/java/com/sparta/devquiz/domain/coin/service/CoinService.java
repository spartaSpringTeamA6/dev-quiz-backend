package com.sparta.devquiz.domain.coin.service;

import com.sparta.devquiz.domain.coin.dto.request.CoinSaveRequest;
import com.sparta.devquiz.domain.coin.dto.request.CoinUseRequest;
import com.sparta.devquiz.domain.coin.dto.response.CoinGetInfoResponse;
import com.sparta.devquiz.domain.coin.dto.response.CoinUseResponse;
import com.sparta.devquiz.domain.coin.entity.Coin;
import com.sparta.devquiz.domain.coin.enums.CoinContent;
import com.sparta.devquiz.domain.coin.exception.CoinCustomException;
import com.sparta.devquiz.domain.coin.exception.CoinExceptionCode;
import com.sparta.devquiz.domain.coin.repository.CoinRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoinService {

    private final CoinRepository coinRepository;
    private final UserService userService;

    public void saveCoin(Long userId, CoinSaveRequest coinSaveRequest, User authUser) {
        userService.validateUser(authUser, userId);

        CoinContent coinContent = coinSaveRequest.getCoinContent();
        if (coinContent == null) {
            throw new CoinCustomException(CoinExceptionCode.BAD_REQUEST_COIN);
        }

        Coin coin = Coin.saveCoins(authUser, coinContent);
        authUser.getCoinList().add(coin);   // 사용시 유저 코인리스트 실시간 반영.  사용하지 않으면 유저가 직접 DB에서 불러와서 업데이트
        coinRepository.save(coin);
    }

    public CoinUseResponse useCoin(Long userId, CoinUseRequest coinUseRequest, User authUser) {
        userService.validateUser(authUser, userId);

        int totalCoin = getTotalCoin(userId);

        int payment = coinUseRequest.getCoinContent().getCoinSupplier().get();

        if (totalCoin < payment) {
            throw new CoinCustomException(CoinExceptionCode.BAD_REQUEST_NOT_ENOUGH_COIN);
        }

        int changeCoins = totalCoin - payment;

        CoinContent coinContent = coinUseRequest.getCoinContent();
        Coin coin = Coin.useCoins(authUser, coinContent);
        coinRepository.save(coin);

        return CoinUseResponse.of(changeCoins);
    }

    public CoinGetInfoResponse getCoinInfo(Long userId, User authUser) {
        userService.validateUser(authUser, userId);

        int totalCoin = getTotalCoin(userId);

        return CoinGetInfoResponse.of(totalCoin);
    }

    private int getTotalCoin(Long userId) {
        return coinRepository.findAllByUserId(userId).stream()
                .mapToInt(Coin::getCoins)
                .sum();
    }
}
