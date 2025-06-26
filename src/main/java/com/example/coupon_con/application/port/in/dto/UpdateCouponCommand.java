package com.example.coupon_con.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.coupon_con.application.port.in.dto
 * fileName       : UdateCouponCommand
 * author         : JAEIK
 * date           : 6/25/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/25/25       JAEIK       최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCouponCommand {
    private Long couponId;
    private String couponName;
    private String couponNumber;
    private Integer quantity;
    private Boolean isDeleted;

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
}

