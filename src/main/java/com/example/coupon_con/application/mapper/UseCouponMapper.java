package com.example.coupon_con.application.mapper;

import com.example.coupon_con.application.port.in.command.UseCouponCommand;
import com.example.coupon_con.application.port.in.dto.UseCouponRequest;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.application.mapper
 * fileName       : UserCouponMapper
 * author         : JAEIK
 * date           : 8/5/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/5/25       JAEIK       최초 생성
 */
@Component
public class UseCouponMapper {
    public UseCouponCommand mapToCommand(UseCouponRequest request) {
        return UseCouponCommand.builder()
                .couponNumber(request.getCouponNumber()).build();
    }

    public UseCouponRequest mapToRequest(UseCouponCommand useCouponCommand) {
        return UseCouponRequest.builder()
                .couponNumber(useCouponCommand.getCouponNumber()).build();
    }
}
