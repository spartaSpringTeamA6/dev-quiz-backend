package com.sparta.devquiz.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setValues(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public String getValues(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    public void setBlacklist(String key, String value, long timeout) {
        redisTemplate.opsForValue().set("blacklist: " + key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public boolean hasBlacklist(String key, String accessToken) {
        String blacklistKey = "blacklist: " + key;
        return redisTemplate.hasKey(blacklistKey) && (redisTemplate.opsForValue().get(blacklistKey)).equals(accessToken);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasValues(String key) {
        return redisTemplate.hasKey(key);
    }
}