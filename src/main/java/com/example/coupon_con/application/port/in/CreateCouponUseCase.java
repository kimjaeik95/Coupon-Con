package com.example.coupon_con.application.port.in;

import com.example.coupon_con.application.port.in.dto.CreateCouponCommand;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.application.port.in
 * fileName       : CreateCouponUseCase
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
public interface CreateCouponUseCase {
    void createCoupon(CreateCouponCommand couponCommand);
}
