package com.example.coupon_con.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.coupon_con.application.port.in.dto
 * fileName       : CouponIssueMessage
 * author         : JAEIK
 * date           : 7/2/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/2/26        JAEIK       최초 생성
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponIssueMessage {
    // RabbitMQ 는 Pojo 객체로 넘기는게 좋다.
    private Long memberId;
    private Long couponId;

    public static CouponIssueMessage of(Long memberId, Long couponId) {
        return CouponIssueMessage.builder()
                .memberId(memberId)
                .couponId(couponId)
                .build();
    }
}
