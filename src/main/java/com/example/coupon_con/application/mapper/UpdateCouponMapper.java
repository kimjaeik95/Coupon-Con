package com.example.coupon_con.application.mapper;

import com.example.coupon_con.application.port.in.command.UpdateCouponCommand;
import com.example.coupon_con.application.port.in.dto.UpdateCouponRequest;
import com.example.coupon_con.domain.Coupon;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.application.mapper
 * fileName       : CreateCouponMapper
 * author         : JAEIK
 * date           : 8/5/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/5/25       JAEIK       최초 생성
 */
@Component
public class UpdateCouponMapper {
    // UpdateCouponCommand -> Domain
    public Coupon mapUpdateCouponDomain(UpdateCouponCommand updateCouponCommand) {
        return Coupon.builder()
                .couponName(updateCouponCommand.getCouponName())
                .couponNumber(updateCouponCommand.getCouponNumber())
                .quantity(updateCouponCommand.getQuantity())
                .isDeleted(updateCouponCommand.getIsDeleted())
                .build();
    }
    // UpdateRequest -> UpdateCouponCommand
    public UpdateCouponCommand toUpdateCouponCommand(UpdateCouponRequest updateCouponRequest) {
        return UpdateCouponCommand.builder()
                .couponName(updateCouponRequest.getCouponName())
                .couponNumber(updateCouponRequest.getCouponNumber())
                .quantity(updateCouponRequest.getQuantity())
                .isDeleted(updateCouponRequest.getIsDeleted())
                .build();
    }
}
