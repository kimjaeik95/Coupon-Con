package com.example.coupon_con.application.port.in;

import com.example.coupon_con.domain.Coupon;

/**
 * packageName    : com.example.coupon_con.application.port.in
 * fileName       : IssueCouponToMemberUseCase
 * author         : JAEIK
 * date           : 6/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/28/25       JAEIK       최초 생성
 */
public interface IssueCouponToMemberUseCase {

    Coupon issueCouponWithAtomicDbUpdate(Long memberId, Long couponId);

    Coupon issueCouponWithDomainLogic(Long memberId, Long couponId);
}
