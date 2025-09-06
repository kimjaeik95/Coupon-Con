package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.MemberCouponIssue;

import java.util.List;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : IssueCouponAllMemberPort
 * author         : JAEIK
 * date           : 9/2/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/2/25        JAEIK       최초 생성
 */
public interface IssueCouponAllMemberPort {
    void saveAllMemberCouponIssue(List<MemberCouponIssue> memberCouponIssues);
}
