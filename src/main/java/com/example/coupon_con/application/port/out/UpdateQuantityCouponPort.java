package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.Coupon;

import java.util.Optional;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : UpdateQuantityCouponPrt
 * author         : JAEIK
 * date           : 7/4/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/4/25       JAEIK       최초 생성
 */
public interface UpdateQuantityCouponPort {
    // DB 에서 직접 수량 차감, 제약조건
    Optional<Coupon> updateMinusCouponQuantity(Long couponId);

    // 단순 업데이트
    void updateQuantity(Coupon coupon);

}
