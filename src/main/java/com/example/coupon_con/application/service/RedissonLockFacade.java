package com.example.coupon_con.application.service;

import com.example.coupon_con.application.callback.RedisLockService;
import com.example.coupon_con.application.port.in.dto.CouponIssueMessage;
import com.example.coupon_con.application.port.out.FindCouponPort;
import com.example.coupon_con.application.port.out.FindMemberPort;
import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.infrastructure.adapter.out.messaging.CouponIssueMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


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
    private final FindMemberPort findMemberPort;
    private final FindCouponPort findCouponPort;
    private final RedisLockService redisLockService;
    private final CouponIssueService couponIssueService;
    private final CouponIssueMessagePublisher couponIssueMessagePublisher;

    // 코드 내부에 redisson Lock 실행
    public void issueCouponWithRedissonLockAsync(Long memberId, Long couponId) {
        findMemberPort.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        findCouponPort.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        CouponIssueMessage couponIssueMessage = CouponIssueMessage.of(memberId, couponId);

        couponIssueMessagePublisher.publish(couponIssueMessage);

    }

    // 콜백 형식
    public Coupon issueCouponWithCallBackRedissonLock(Long memberId, Long couponId) {
        return redisLockService.callWithLock(couponId, () -> couponIssueService.issueCouponNormally(memberId, couponId));
    }
}
