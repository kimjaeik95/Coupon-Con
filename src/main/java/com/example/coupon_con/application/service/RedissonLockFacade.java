package com.example.coupon_con.application.service;

import com.example.coupon_con.application.callback.RedisLockService;
import com.example.coupon_con.application.port.in.dto.CouponIssueMessage;
import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.infrastructure.adapter.out.messaging.CouponIssueMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

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
    private final RedisLockService redisLockService;
    private final CouponIssueService couponIssueService;
    private final CouponIssueMessagePublisher couponIssueMessagePublisher;

    // 직접 락 획득 방식
    public Coupon issueCouponWithRedissonLock(Long memberId, Long couponId) {
        String lockKey = "coupon:lock:" + couponId;
        // 락 조작을 위한 객체 생성
        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;
        Coupon coupon;
        try {
            // waitTime: 5초 락 점유 기다리기 leaseTime: 3초동안 락점유,이후 락 해제, timeUnit: 시간단위
            // tryLock() 성공하면 락 고유 value 자동으로 생성된다.
            isLocked = lock.tryLock(30, 3, TimeUnit.SECONDS);

            if (!isLocked) {
                throw new IllegalArgumentException("쿠폰 발급 락 획득 실패");
            }

            coupon = couponIssueService.issueCouponNormally(memberId, couponId);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

        CouponIssueMessage couponIssueMessage = CouponIssueMessage.of(memberId, couponId);
        couponIssueMessagePublisher.publish(couponIssueMessage);
        return coupon;
    }


    // 콜백 형식
    public Coupon issueCouponWithCallBackRedissonLock(Long memberId, Long couponId) {
        return redisLockService.callWithLock(couponId, () -> couponIssueService.issueCouponNormally(memberId, couponId));
    }
}
