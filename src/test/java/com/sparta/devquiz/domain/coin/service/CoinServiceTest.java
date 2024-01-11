package com.sparta.devquiz.domain.coin.service;

import com.sparta.devquiz.domain.coin.dto.request.SaveCoinRequest;
import com.sparta.devquiz.domain.coin.dto.request.UseCoinRequest;
import com.sparta.devquiz.domain.coin.dto.response.GetCoinInfoResponse;
import com.sparta.devquiz.domain.coin.dto.response.UseCoinResponse;
import com.sparta.devquiz.domain.coin.entity.Coin;
import com.sparta.devquiz.domain.coin.enums.CoinContent;
import com.sparta.devquiz.domain.coin.exception.CoinCustomException;
import com.sparta.devquiz.domain.coin.repository.CoinRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.OauthType;
import com.sparta.devquiz.domain.user.enums.UserRole;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import com.sparta.devquiz.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
//@DataJpaTest
class CoinServiceTest {

    @InjectMocks
    CoinService coinService;
    @Mock
    UserService userService;
    @Mock
    CoinRepository coinRepository;
    @Mock
    UserRepository userRepository;

    private User user;
    private User authUser;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        coinRepository.deleteAll();

        user = new User(1L,"test", OauthType.GOOGLE, "123123","testuser",
                UserRole.USER, 0L, null, false, null, new ArrayList<>(), null);

        userRepository.save(user);
        authUser = user;
    }
    @Test
    @DisplayName("코인 저장")
    void saveCoin() {
        // GIVEN
        Long userId = 1L;

        SaveCoinRequest saveCoinRequest1 = new SaveCoinRequest(0L, CoinContent.FIRST);
        SaveCoinRequest saveCoinRequest2 = new SaveCoinRequest(0L, CoinContent.CORRECT);
        SaveCoinRequest saveCoinRequest3 = new SaveCoinRequest(0L, CoinContent.FAIL);
        SaveCoinRequest saveCoinRequest4 = new SaveCoinRequest(0L, CoinContent.PASS);

        when(userService.getUserById(userId)).thenReturn(user);

        // WHEN
        coinService.saveCoin(userId, saveCoinRequest1, user);
        coinService.saveCoin(userId, saveCoinRequest2, user);
        coinService.saveCoin(userId, saveCoinRequest3, user);
        coinService.saveCoin(userId, saveCoinRequest4, user);

        // THEN
        assertEquals(20L, Coin.saveCoins(user, saveCoinRequest1.getCoinContent()).getCoins());
        assertEquals(10L, Coin.saveCoins(user, saveCoinRequest2.getCoinContent()).getCoins());
        assertEquals(5L, Coin.saveCoins(user, saveCoinRequest3.getCoinContent()).getCoins());
        assertEquals(0L, Coin.saveCoins(user, saveCoinRequest4.getCoinContent()).getCoins());
    }

    @Test
    @DisplayName("코인 사용")
    void useCoin() {
        // GIVEN
        Long userId = 1L;
        SaveCoinRequest saveCoinRequest1 = new SaveCoinRequest(0L, CoinContent.FIRST);
        SaveCoinRequest saveCoinRequest2 = new SaveCoinRequest(0L, CoinContent.FIRST);

        Coin coin1 = Coin.saveCoins(user, saveCoinRequest1.getCoinContent());
        Coin coin2 = Coin.saveCoins(user, saveCoinRequest2.getCoinContent());

        Long totalCoin = coin1.getCoins() + coin2.getCoins();
        Long payment = 30L;

        List<Coin> coins = Arrays.asList(coin1, coin2);

        when(userService.getUserById(userId)).thenReturn(user);
        when(coinRepository.findAllByUserId(userId)).thenReturn(coins);

        UseCoinRequest useCoinRequest = new UseCoinRequest(totalCoin, payment);

        // WHEN
        UseCoinResponse response = coinService.useCoin(userId, useCoinRequest, user);

        // THEN
        assertEquals(10L, response.getCoins());
    }
    @Test
    @WithMockUser(username = "testuser", roles ="USER")
    @DisplayName("코인 사용 실패 - 한도 초과")
    void failToUseCoin() {
        // GIVEN
        Long userId = 1L;
        SaveCoinRequest saveCoinRequest1 = new SaveCoinRequest(0L, CoinContent.FIRST);
        SaveCoinRequest saveCoinRequest2 = new SaveCoinRequest(0L, CoinContent.FIRST);

        Coin coin1 = Coin.saveCoins(user, saveCoinRequest1.getCoinContent());
        Coin coin2 = Coin.saveCoins(user, saveCoinRequest2.getCoinContent());

        Long totalCoin = coin1.getCoins() + coin2.getCoins();
        Long payment = 50L;

        List<Coin> coins = Arrays.asList(coin1, coin2);

        when(userService.getUserById(userId)).thenReturn(user);
        when(coinRepository.findAllByUserId(userId)).thenReturn(coins);

        UseCoinRequest useCoinRequest = new UseCoinRequest(totalCoin, payment);

        // WHEN THEN
        CoinCustomException exception = assertThrows(
                CoinCustomException.class,
                () -> coinService.useCoin(userId, useCoinRequest, authUser)
        );
        assertEquals("COIN-002", exception.getErrorCode());
        assertEquals("포인트가 부족합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("총 보유 코인 조회")
    void getCoinInfo() {
        // GIVEN
        Long userId = 1L;

        SaveCoinRequest saveCoinRequest1 = new SaveCoinRequest(0L, CoinContent.FIRST);
        SaveCoinRequest saveCoinRequest2 = new SaveCoinRequest(0L, CoinContent.FIRST);

        Coin coin1 = Coin.saveCoins(user, saveCoinRequest1.getCoinContent());
        Coin coin2 = Coin.saveCoins(user, saveCoinRequest2.getCoinContent());

        List<Coin> coins = Arrays.asList(coin1, coin2);

        when(userService.getUserById(userId)).thenReturn(user);
        when(coinRepository.findAllByUserId(userId)).thenReturn(coins);

        // WHEN
        GetCoinInfoResponse response = coinService.getCoinInfo(userId, user);

        // THEN
        assertEquals(40L, response.getCoins());
    }
}