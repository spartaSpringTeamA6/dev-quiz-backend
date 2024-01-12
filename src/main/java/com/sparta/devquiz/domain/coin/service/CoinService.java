package com.sparta.devquiz.domain.coin.service;

import com.sparta.devquiz.domain.coin.dto.request.SaveCoinRequest;
import com.sparta.devquiz.domain.coin.dto.request.UseCoinRequest;
import com.sparta.devquiz.domain.coin.dto.response.GetCoinInfoResponse;
import com.sparta.devquiz.domain.coin.dto.response.SaveCoinResponse;
import com.sparta.devquiz.domain.coin.dto.response.UseCoinResponse;
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

    public SaveCoinResponse saveCoin(Long userId, SaveCoinRequest saveCoinRequest, User authUser) {
        User user = userService.getUserById(userId);
        checkUserPermission(authUser, user);

        CoinContent coinContent = saveCoinRequest.getCoinContent();
        if (coinContent == null) {
            throw new CoinCustomException(CoinExceptionCode.BAD_REQUEST_COIN);
        }

        Coin coin = Coin.saveCoins(user, coinContent);
        user.getCoinList().add(coin);   // 사용시 유저 코인리스트 실시간 반영.  사용하지 않으면 유저가 직접 DB에서 불러와서 업데이트
        coinRepository.save(coin);

        return new SaveCoinResponse();
    }

    public UseCoinResponse useCoin(Long userId, UseCoinRequest useCoinRequest, User authUser) {
        User user = userService.getUserById(userId);
        checkUserPermission(authUser, user);

        int totalCoin = getTotalCoin(userId);

        int payment = useCoinRequest.getCoinContent().getCoinSupplier().get();

        if (totalCoin < payment) {
            throw new CoinCustomException(CoinExceptionCode.BAD_REQUEST_NOT_ENOUGH_COIN);
        }

        int changeCoins = totalCoin - payment;

        CoinContent coinContent = useCoinRequest.getCoinContent();
        Coin coin = Coin.useCoins(user, coinContent);
        coinRepository.save(coin);

        return new UseCoinResponse(changeCoins);
    }

    public GetCoinInfoResponse getCoinInfo(Long userId, User authUser) {
        User user = userService.getUserById(userId);
        checkUserPermission(authUser, user);

        int totalCoin = getTotalCoin(userId);

        return new GetCoinInfoResponse(totalCoin);
    }

    private void checkUserPermission(User authUser, User user) {
        if (!user.getUsername().equals(authUser.getUsername())) {
            throw new CoinCustomException(CoinExceptionCode.NOT_FOUND_COIN_USER);
        }
    }

    private int getTotalCoin(Long userId) {
        return coinRepository.findAllByUserId(userId).stream()
                .mapToInt(Coin::getCoins)
                .sum();
    }
}
