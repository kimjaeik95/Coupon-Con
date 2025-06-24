package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.Coupon;

import java.util.List;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : GetAllCouponPort
 * author         : JAEIK
 * date           : 6/24/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/24/25       JAEIK       최초 생성
 */
public interface GetAllCouponPort {
    List<Coupon> findAll();
}
