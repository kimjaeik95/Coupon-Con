package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.MemberCouponIssue;

import java.util.Optional;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : FindIssueCouponPort
 * author         : JAEIK
 * date           : 8/5/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/5/25       JAEIK       최초 생성
 */
public interface FindIssueCouponPort {
    Optional<MemberCouponIssue> findIssueCoupon(Long memberId, Long couponId);
}
