package com.example.coupon_con.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName    : com.example.coupon_con.domain
 * fileName       : Coupon
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    private Long couponId;

    private String couponName;

    private String couponNumber;

    private Instant couponCreatedAt;

    private Instant couponExpiredAt;

    private Integer quantity;

    private Boolean isDeleted;
    private Long version;

    public void updateCoupon (String couponName, String couponNumber, Integer quantity, Boolean isDeleted) {
        this.couponName = couponName;
        this.couponNumber = couponNumber;
        this.quantity = quantity;
        this.isDeleted = isDeleted;
    }

    // 도메인 내 메서드식
    public void decreaseQuantity() {
        if (quantity <= 0) {
            throw new IllegalArgumentException("쿠폰 수량이 부족합니다.");
        }
        quantity -= 1;
    }
}

