package com.example.coupon_con.application.port.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateCouponRequest {
    // 외부에 노출되므로 Validation 의 집중
    @NotBlank
    private String couponName;
    @NotBlank
    private String couponNumber;
    @NotNull(message = "수량은 필수 입니다.")
    private Integer quantity;

    public CreateCouponCommand toCreateCouponCommand() {
        return new CreateCouponCommand(couponName, couponNumber, quantity);
    }
}
