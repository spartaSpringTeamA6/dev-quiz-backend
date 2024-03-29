package com.sparta.devquiz.domain.coin.service;

import com.sparta.devquiz.domain.coin.dto.request.CoinUseRequest;
import com.sparta.devquiz.domain.coin.dto.response.CoinGetInfoResponse;
import com.sparta.devquiz.domain.coin.dto.response.CoinUseResponse;
import com.sparta.devquiz.domain.coin.entity.Coin;
import com.sparta.devquiz.domain.coin.enums.CoinContent;
import com.sparta.devquiz.domain.coin.exception.CoinCustomException;
import com.sparta.devquiz.domain.coin.exception.CoinExceptionCode;
import com.sparta.devquiz.domain.coin.repository.CoinRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoinService {

    private final CoinRepository coinRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveCoin(Long userId, CoinContent coinContent, User authUser) {
        if (!authUser.getId().equals(userId)) {
            throw new UserCustomException(UserExceptionCode.BAD_REQUEST_USER_ID);
        }
        User user = userRepository.findByIdOrElseThrow(userId);

        if (coinContent == null) {
            throw new CoinCustomException(CoinExceptionCode.BAD_REQUEST_COIN);
        }

        Coin coin = Coin.saveCoins(authUser, coinContent);
        user.updateCoin(coin);
        coinRepository.save(coin);
    }

    @Transactional
    public CoinUseResponse useCoin(Long userId, CoinUseRequest coinUseRequest, User authUser) {
        if (!authUser.getId().equals(userId)) {
            throw new UserCustomException(UserExceptionCode.BAD_REQUEST_USER_ID);
        }
        userRepository.findByIdOrElseThrow(userId);

        int totalCoin = getTotalCoin(userId);

        int payment = coinUseRequest.getCoinContent().getCoin();

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
        if (!authUser.getId().equals(userId)) {
            throw new UserCustomException(UserExceptionCode.BAD_REQUEST_USER_ID);
        }
        userRepository.findByIdOrElseThrow(userId);

        int totalCoin = getTotalCoin(userId);

        return CoinGetInfoResponse.of(totalCoin);
    }

    private int getTotalCoin(Long userId) {
        return coinRepository.findAllByUserId(userId).stream()
                .mapToInt(Coin::getCoins)
                .sum();
    }
}
