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
import com.sparta.devquiz.domain.user.repository.UserRepository;
import com.sparta.devquiz.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
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
        user = new User(1L,"test", null, "123123","testuser",
                null, 0, 0,false, null, null, new ArrayList<>(), null, null, null, null);

        userRepository.save(user);
        authUser = user;
    }
    @Test
    @DisplayName("코인 저장")
    void saveCoin() {
        // GIVEN
        Long userId = 1L;

        List<SaveCoinRequest> saveCoinRequestList = Arrays.asList(
        new SaveCoinRequest(0, CoinContent.FIRST),
        new SaveCoinRequest(0, CoinContent.CORRECT),
        new SaveCoinRequest(0, CoinContent.FAIL),
        new SaveCoinRequest(0, CoinContent.PASS)
        );
        lenient().when(userService.getUserById(userId)).thenReturn(user);

        List<Coin> testCoinList = new ArrayList<>();
        for (SaveCoinRequest request : saveCoinRequestList) {
            testCoinList.add(Coin.saveCoins(user, request.getCoinContent()));
        }
        when(coinRepository.findAllByUserId(userId)).thenReturn(testCoinList);

        // WHEN
        for (SaveCoinRequest request : saveCoinRequestList) {
            coinService.saveCoin(userId, request, user);
        }

        // THEN
        List<Coin> saveCoinList = coinRepository.findAllByUserId(userId);
        for (int i = 0; i < saveCoinRequestList.size(); i++) {
            SaveCoinRequest request = saveCoinRequestList.get(i);
            Coin findSaveCoins = saveCoinList.get(i);
            assertEquals(Coin.saveCoins(user, request.getCoinContent()).getCoins(), findSaveCoins.getCoins());
        }
    }

    @Test
    @DisplayName("코인 사용")
    void useCoin() {
        // GIVEN
        Long userId = 1L;
        UseCoinRequest useCoinRequest = new UseCoinRequest(0, CoinContent.ITEM_CAT);

        SaveCoinRequest saveCoinRequest1 = new SaveCoinRequest(0, CoinContent.FIRST);
        SaveCoinRequest saveCoinRequest2 = new SaveCoinRequest(0, CoinContent.FIRST);

        Coin coin1 = Coin.saveCoins(user, saveCoinRequest1.getCoinContent());
        Coin coin2 = Coin.saveCoins(user, saveCoinRequest2.getCoinContent());

        int totalCoin = coin1.getCoins() + coin2.getCoins();
        int payment = useCoinRequest.getCoinContent().getCoinSupplier().get();

        List<Coin> coins = Arrays.asList(coin1, coin2);

        lenient().when(userService.getUserById(userId)).thenReturn(user);
        when(coinRepository.findAllByUserId(userId)).thenReturn(coins);


        // WHEN
        UseCoinResponse response = coinService.useCoin(userId, useCoinRequest, user);

        // THEN
        assertEquals(15, response.getCoins());
    }
    @Test
    @DisplayName("코인 사용 실패 - 한도 초과")
    void failToUseCoin() {
        // GIVEN
        Long userId = 1L;
        UseCoinRequest useCoinRequest = new UseCoinRequest(0, CoinContent.ITEM_DOG);
        SaveCoinRequest saveCoinRequest1 = new SaveCoinRequest(0, CoinContent.FIRST);
        SaveCoinRequest saveCoinRequest2 = new SaveCoinRequest(0, CoinContent.FIRST);

        Coin coin1 = Coin.saveCoins(user, saveCoinRequest1.getCoinContent());
        Coin coin2 = Coin.saveCoins(user, saveCoinRequest2.getCoinContent());

        int totalCoin = coin1.getCoins() + coin2.getCoins();

        List<Coin> coins = Arrays.asList(coin1, coin2);

        lenient().when(userService.getUserById(userId)).thenReturn(user);
        when(coinRepository.findAllByUserId(userId)).thenReturn(coins);


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

        SaveCoinRequest saveCoinRequest1 = new SaveCoinRequest(0, CoinContent.FIRST);
        SaveCoinRequest saveCoinRequest2 = new SaveCoinRequest(0, CoinContent.FIRST);

        Coin coin1 = Coin.saveCoins(user, saveCoinRequest1.getCoinContent());
        Coin coin2 = Coin.saveCoins(user, saveCoinRequest2.getCoinContent());

        List<Coin> coins = Arrays.asList(coin1, coin2);

        lenient().when(userService.getUserById(userId)).thenReturn(user);
        when(coinRepository.findAllByUserId(userId)).thenReturn(coins);

        // WHEN
        GetCoinInfoResponse response = coinService.getCoinInfo(userId, user);

        // THEN
        assertEquals(40, response.getCoins());
    }
}