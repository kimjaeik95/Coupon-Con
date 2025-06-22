package com.example.coupon_con.application.port.in.dto;

/**
 * packageName    : com.example.coupon_con.application.port.in.dto
 * fileName       : CreateCouponRequest
 * author         : JAEIK
 * date           : 6/22/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/22/25       JAEIK       최초 생성
 */
public class CreateCouponRequest {
    private String couponName;
    private String couponNumber;
    private Integer quantity;

    public CreateCouponCommand toCreateCouponCommand() {
        return new CreateCouponCommand(couponName, couponNumber, quantity);
    }
}
