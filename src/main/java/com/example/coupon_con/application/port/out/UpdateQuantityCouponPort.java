package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.Coupon;

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
public interface UpdateQuantityCouponPrt {
    Coupon updateQuantity(Coupon coupon);
}
