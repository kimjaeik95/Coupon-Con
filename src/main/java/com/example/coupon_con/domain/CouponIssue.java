package com.example.coupon_con.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName    : com.example.coupon_con.domain
 * fileName       : CouponIssue
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponIssue {
    private Long couponIssueId;

    private Long couponId;

    private String memberId;

    private Instant issuedAt;

    private Boolean used;

    private Instant usedAt;
}
