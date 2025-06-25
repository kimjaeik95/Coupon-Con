package com.example.coupon_con.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.coupon_con.application.port.in.dto
 * fileName       : DeleteCouponCommand
 * author         : JAEIK
 * date           : 6/25/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/25/25       JAEIK       최초 생성
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCouponCommand {
    private Long couponId;
}
