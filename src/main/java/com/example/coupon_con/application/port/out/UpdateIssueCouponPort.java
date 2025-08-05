package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.MemberCouponIssue;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : UpdateIssueCouponPort
 * author         : JAEIK
 * date           : 8/5/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/5/25       JAEIK       최초 생성
 */
public interface UpdateIssueCouponPort {
    void updateUsedStatus(MemberCouponIssue couponIssue);
}
