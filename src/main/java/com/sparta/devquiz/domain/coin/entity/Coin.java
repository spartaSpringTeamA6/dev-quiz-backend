package com.sparta.devquiz.domain.coin.entity;

import com.sparta.devquiz.domain.coin.enums.CoinContent;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long coins;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Coin saveCoins(User user, CoinContent coinContent) {
        return Coin.builder()
                .user(user)
                .coins(coinContent.getCoinSupplier().get())
                .status(coinContent.getStatus())
                .content(coinContent.getContent())
                .build();
    }

    public static Coin useCoins(User user,CoinContent coinContent) {
        return Coin.builder()
                .user(user)
                .coins(-coinContent.getCoinSupplier().get())
                .status(coinContent.getStatus())
                .content(coinContent.getContent())
                .build();
    }
}
