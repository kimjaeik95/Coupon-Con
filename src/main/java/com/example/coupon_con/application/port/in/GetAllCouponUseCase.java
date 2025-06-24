package com.example.coupon_con.application.port.in;

import com.example.coupon_con.application.port.in.dto.CouponResponse;

import java.util.List;

/**
 * packageName    : com.example.coupon_con.application.port.in
 * fileName       : CouponQueryUseCase
 * author         : JAEIK
 * date           : 6/24/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/24/25       JAEIK       최초 생성
 */
public interface GetAllCouponUseCase {
    // 전체조회는 입력받을게 없으니 GetALlCouponQuery (UseCase 사용되는 Dto) 안 만들어도 된다.
    List<CouponResponse> getAllCoupon();
}
