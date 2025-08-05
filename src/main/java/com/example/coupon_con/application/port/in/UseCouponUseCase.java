package com.example.coupon_con.application.port.in;

import com.example.coupon_con.application.port.in.command.UseCouponCommand;
import com.example.coupon_con.application.port.in.dto.UseCouponResponse;

/**
 * packageName    : com.example.coupon_con.application.port.in
 * fileName       : UseCouponUseCase
 * author         : JAEIK
 * date           : 8/5/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/5/25       JAEIK       최초 생성
 */
public interface UseCouponUseCase {
    UseCouponResponse UseCoupon(Long memberId, UseCouponCommand useCouponCommand);
}
