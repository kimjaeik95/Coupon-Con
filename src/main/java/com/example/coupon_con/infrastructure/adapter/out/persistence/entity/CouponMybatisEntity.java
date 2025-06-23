package com.example.coupon_con.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.persistence.entity
 * fileName       : MybatisEntity
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
public class CouponMybatisEntity {

    private Long couponId;

    private String couponName;

    private String couponNumber;

    private Instant couponCreatedAt;

    private Instant couponExpiredAt;

    private Integer quantity;

    private Boolean isDeleted;
}
