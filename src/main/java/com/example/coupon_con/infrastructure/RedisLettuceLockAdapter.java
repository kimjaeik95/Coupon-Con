package com.example.coupon_con.infrastructure;


import com.example.coupon_con.application.port.out.RedisLettuceLockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out
 * fileName       : RedisLockAdapter
 * author         : JAEIK
 * date           : 7/18/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/18/25       JAEIK       최초 생성
 */
@Component
@RequiredArgsConstructor
public class RedisLettuceLockAdapter implements RedisLettuceLockPort {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean lock(Long key, String lockId) {
        return redisTemplate
                .opsForValue()
                // setnx + Expire 합친 메서드
                .setIfAbsent(generateKey(key), lockId, Duration.ofMillis(3_000));
    }


    @Override
    public boolean unlock(Long key, String lockId) {
        String currentLockId = redisTemplate.opsForValue().get(generateKey(key));
        if (lockId.equals(currentLockId)) {
            return redisTemplate.delete(generateKey(key));
        }
        return false;
    }

    private String generateKey(Long key) {
        return key.toString();
    }
}
