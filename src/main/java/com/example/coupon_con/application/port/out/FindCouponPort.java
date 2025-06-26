package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.Coupon;

import java.util.Optional;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : FindCouponPort
 * author         : JAEIK
 * date           : 6/25/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/25/25       JAEIK       최초 생성
 */
public interface FindCouponPort {
    Optional<Coupon> findById(Long couponId);
}
