package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.MemberCouponIssue;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : IssueCouponToMemberPort
 * author         : JAEIK
 * date           : 6/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/28/25       JAEIK       최초 생성
 */
public interface IssueCouponToMemberPort {
    void saveMemberCouponIssue(MemberCouponIssue memberCouponIssue);
}
