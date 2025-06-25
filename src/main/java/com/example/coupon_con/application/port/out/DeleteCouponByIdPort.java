package com.example.coupon_con.application.port.out;

import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : DeleteCouponPort
 * author         : JAEIK
 * date           : 6/25/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/25/25       JAEIK       최초 생성
 */
public interface DeleteCouponByIdPort {
    void deleteById(Long couponId);
}
