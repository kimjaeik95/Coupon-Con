package com.example.coupon_con.application.port.in.dto;

import com.example.coupon_con.domain.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName    : com.example.coupon_con.application.port.in.dto
 * fileName       : CreateCouponResponse
 * author         : JAEIK
 * date           : 6/22/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/22/25       JAEIK       최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponResponse {
    private Long couponId;

    private String couponName;

    private String couponNumber;

    private Instant couponCreateAt;

    private Instant couponExpiredAt;

    private Integer quantity;

    private Boolean isDeleted;

    public static CreateCouponResponse fromCreateCouponDomain(Coupon coupon) {
        return CreateCouponResponse.builder()
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
