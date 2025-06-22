package com.example.coupon_con.application.port.in.dto;

import com.example.coupon_con.domain.Coupon;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * packageName    : com.example.coupon_con.application.port.in.dto
 * fileName       : CreateCouponCommand
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
@AllArgsConstructor
public class CreateCouponCommand {
    // Command 어떤 행위 (Use Case)을 실행하기 위한 데이터
    private String couponName;
    private String couponNumber;
    private Integer quantity;



    public Coupon toCouponDomain() {
        return Coupon.builder()
                .couponName(couponName)
                .couponNumber(couponNumber)
                .couponCreateAt(Instant.now())
                .couponExpiredAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .quantity(quantity)
                .build();
    }
}
