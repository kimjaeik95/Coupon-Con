package com.example.coupon_con.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.persistence.entity
 * fileName       : CouponIssueMybatisEntity
 * author         : JAEIK
 * date           : 6/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/28/25       JAEIK       최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCouponIssueMybatisEntity {
    private Long couponIssueId;

    private Long couponId;

    private Long memberId;

    private Instant issuedAt;

    private Boolean used;

    private Instant usedAt;

}
