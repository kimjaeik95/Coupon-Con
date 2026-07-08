package com.example.coupon_con.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCouponIssue {
    private Long couponIssueId;

    private Long couponId;

    private Long memberId;

    private Instant issuedAt;

    private Boolean used;

    private Instant usedAt;

    public static MemberCouponIssue forIssue(Long memberId, Long couponId) {
        return MemberCouponIssue.builder()
                .memberId(memberId)
                .couponId(couponId)
                .issuedAt(Instant.now())
                .build();
    }

    // 사용 체크
    public void checkUsed () {
        if (Boolean.TRUE.equals(this.used)) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
    }

    // 사용
    public void updateUsedCoupon () {
        this.used = true;
        this.usedAt = Instant.now();
    }
}
