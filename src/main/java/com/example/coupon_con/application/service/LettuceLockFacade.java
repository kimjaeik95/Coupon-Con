package com.example.coupon_con.application.service;

import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.infrastructure.RedisLockAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * packageName    : com.example.coupon_con.application.service
 * fileName       : LettuceLockFacade
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
public class LettuceLockFacade {
    private final CouponIssueService couponIssueService;
    private final RedisLockAdapter redisLockAdapter;

    public Coupon issueCouponWithLettuceLock(Long memberId, Long couponId) throws InterruptedException {
        // "lock" 으로 value 값이 고정되면 내 락이 아닌 것도 해제할 수 있는 위험이 있기 때문에 UUID 사용
        String lockId = UUID.randomUUID().toString();
        // 스레드의 무한 락대기를 방지하기위해서 타임아웃 설정  && 서비스로직에서도 재고 0이면 예외로 로직 종료
        int retry = 30;

        while (!redisLockAdapter.lock(couponId, lockId) && retry-- > 0) {
            Thread.sleep(100);
        }

        if (retry <= 0) {
            throw new IllegalStateException("락 획득 실패 - 재고 소진 또는 과부하");
        }

        try {
            return couponIssueService.issueCouponNormally(memberId , couponId);
        } finally {
            redisLockAdapter.unlock(couponId, lockId);
        }
    }
}
