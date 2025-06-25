package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.Coupon;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.persistence.repository
 * fileName       : CouponRepository
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
public interface CreateCouponPort {
    void save (Coupon coupon);
}
