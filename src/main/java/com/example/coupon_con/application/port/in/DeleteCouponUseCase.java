package com.example.coupon_con.application.port.in;

import com.example.coupon_con.application.port.in.dto.DeleteCouponCommand;

/**
 * packageName    : com.example.coupon_con.application.port.in
 * fileName       : DeleteByCouponUseCase
 * author         : JAEIK
 * date           : 6/25/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/25/25       JAEIK       최초 생성
 */
public interface DeleteCouponUseCase {
    void deleteByIdCoupon(DeleteCouponCommand command);
}
