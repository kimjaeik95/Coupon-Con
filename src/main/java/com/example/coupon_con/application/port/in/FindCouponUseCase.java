package com.example.coupon_con.application.port.in;

import com.example.coupon_con.domain.Coupon;

/**
 * packageName    : com.example.coupon_con.application.port.in
 * fileName       : FindCouponUseCase
 * author         : JAEIK
 * date           : 6/25/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/25/25       JAEIK       최초 생성
 */
public interface FindCouponUseCase {
    Coupon findByCoupon(Long couponId);
}
