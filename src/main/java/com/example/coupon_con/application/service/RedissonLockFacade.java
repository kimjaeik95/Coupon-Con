package com.example.coupon_con.application.service;

import com.example.coupon_con.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * packageName    : com.example.coupon_con.application.service
 * fileName       : RedissonLockFacade
 * author         : JAEIK
 * date           : 7/23/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/23/25       JAEIK       최초 생성
 */
@Component
@RequiredArgsConstructor
public class RedissonLockFacade {
    private final RedissonClient redissonClient;

    private final CouponIssueService couponIssueService;

    public Coupon issueCouponWithRedissonLock(Long memberId, Long couponId) {
        String lockKey = "coupon:lock:" + couponId;
        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;
        try {
            // waitTime: 5초 락 점유 기다리기 leaseTime: 3초동안 락점유,이후 락 해제, timeUnit: 시간단위
            isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);

            if (!isLocked) {
                throw new IllegalArgumentException("쿠폰 발급 락 획득 실패");
            }

            return couponIssueService.issueCouponNormally(memberId, couponId);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 락을 획득하지 못했을때 && 다른 스레드가 락을 가진 경우 방지 (UUID 로 그럴 확률은 없긴하다.)
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
