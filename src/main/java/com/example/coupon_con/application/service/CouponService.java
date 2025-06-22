package com.example.coupon_con.application.service;

import com.example.coupon_con.application.port.in.CreateCouponUseCase;
import com.example.coupon_con.application.port.in.dto.CreateCouponCommand;
import com.example.coupon_con.application.port.out.CreateCouponPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.example.coupon_con.application.service
 * fileName       : CouponService
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
@RequiredArgsConstructor
@Service
public class CouponService implements CreateCouponUseCase {
    private final CreateCouponPort createCouponPort;

    @Override
    public void createCoupon(CreateCouponCommand couponCommand) {
        createCouponPort.save(couponCommand.toCouponDomain());
    }
}
