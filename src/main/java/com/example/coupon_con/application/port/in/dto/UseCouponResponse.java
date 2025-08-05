package com.example.coupon_con.application.port.in.dto;

import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.domain.MemberCouponIssue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * packageName    : com.example.coupon_con.application.port.in.dto
 * fileName       : UseCouponResponse
 * author         : JAEIK
 * date           : 8/5/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/5/25       JAEIK       최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UseCouponResponse {
    private String couponName;
    private String discountAmount;
    private Instant usedAt;
    private String message;

    public static UseCouponResponse fromResponse(String couponName, Instant usedAt) {
        return UseCouponResponse.builder()
                .couponName(couponName)
                .discountAmount("10% 할인")
                .usedAt(usedAt)
                .message("쿠폰 성공적으로 사용 되었습니다.")
                .build();
    }
}
