package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.Coupon;

import java.util.Optional;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : IssueCouponToMemberPort
 * author         : JAEIK
 * date           : 6/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/28/25       JAEIK       최초 생성
 */
public interface IssueCouponToMemberPort {
    Optional<Coupon> minusCouponQuantity(Long memberId, Long couponId);
}
