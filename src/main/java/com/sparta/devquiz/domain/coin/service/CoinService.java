package com.sparta.devquiz.domain.coin.service;

import com.sparta.devquiz.domain.coin.dto.request.SaveCoinRequest;
import com.sparta.devquiz.domain.coin.dto.request.UseCoinRequest;
import com.sparta.devquiz.domain.coin.dto.response.GetCoinInfoResponse;
import com.sparta.devquiz.domain.coin.dto.response.SaveCoinResponse;
import com.sparta.devquiz.domain.coin.dto.response.UseCoinResponse;
import com.sparta.devquiz.domain.coin.entity.Coin;
import com.sparta.devquiz.domain.coin.exception.CoinCustomException;
import com.sparta.devquiz.domain.coin.exception.CoinExceptionCode;
import com.sparta.devquiz.domain.coin.repository.CoinRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import com.sparta.devquiz.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinService {

    private final CoinRepository coinRepository;
    private final UserRepository userRepository;

    UserService userService = new UserService(userRepository);

//    public SaveCoinResponse saveCoin(Long userId, SaveCoinRequest saveCoinRequest, User authUser) {
//        User user = userService.getUserById(userId);
//        checkUserPermission(authUser, user);
//
//        // enum 확인
//        Coin coin = // enum 확인 하기
//        coinRepository.save(coin);
//
//        return new SaveCoinResponse();
//    }
//
//    public UseCoinResponse useCoin(Long userId, UseCoinRequest useCoinRequest, User authUser) {
//        User user = userService.getUserById(userId);
//        checkUserPermission(authUser, user);
//
//        Coin coin =
//
//        return new UseCoinResponse(coin);
//    }

    public GetCoinInfoResponse getCoinInfo(Long userId, User authUser) {
        User user = userService.getUserById(userId);
        checkUserPermission(authUser, user);

        Long totalPoint = coinRepository.findByUserId(userId).stream()
                .mapToLong(Coin::getCoins)
                .sum();

        return new GetCoinInfoResponse(totalPoint);
    }

    private void checkUserPermission(User authUser, User user) {
        if (!user.getNickname().equals(authUser.getNickname())) {
            throw new CoinCustomException(CoinExceptionCode.NOT_FOUND_COIN_USER);
        }
    }
}
