package com.example.coupon_con.application.mapper;

import com.example.coupon_con.application.port.in.dto.CouponResponse;
import com.example.coupon_con.application.port.in.dto.CreateCouponCommand;
import com.example.coupon_con.domain.Coupon;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * packageName    : com.example.coupon_con.application.mapper
 * fileName       : CouponDtoMapper
 * author         : JAEIK
 * date           : 6/24/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/24/25       JAEIK       최초 생성
 */
@Component
public class CouponDtoMapper {
    // CreateCouponCommand -> Domain 변환
    public Coupon toCouponDomain(CreateCouponCommand command) {
        return Coupon.builder()
                .couponName(command.getCouponName())
                .couponNumber(command.getCouponNumber())
                .couponCreatedAt(Instant.now())
                .couponExpiredAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .quantity(command.getQuantity())
                .build();
    }

    // Domain ->  CouponResponse Dto 변환
    public CouponResponse toCouponResponseDto(Coupon coupon) {
        return CouponResponse.builder()
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .couponNumber(coupon.getCouponNumber())
                .couponCreateAt(coupon.getCouponCreatedAt())
                .couponExpiredAt(coupon.getCouponExpiredAt())
                .quantity(coupon.getQuantity())
                .isDeleted(coupon.getIsDeleted())
                .build();
    }

}
