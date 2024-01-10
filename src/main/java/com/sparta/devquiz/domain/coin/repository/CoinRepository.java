package com.sparta.devquiz.domain.coin.repository;

import com.sparta.devquiz.domain.coin.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    List<Coin> findByUserId(Long userId);
}
