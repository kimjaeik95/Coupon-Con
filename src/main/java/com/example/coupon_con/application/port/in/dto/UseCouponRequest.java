package com.example.coupon_con.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.example.coupon_con.application.port.in.dto
 * fileName       : UseCouponRequest
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
@RequiredArgsConstructor
public class UseCouponRequest {
    private String couponNumber;
}
