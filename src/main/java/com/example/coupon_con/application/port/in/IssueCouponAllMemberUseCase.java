package com.example.coupon_con.application.port.in;
import com.example.coupon_con.domain.Member;

import java.util.List;

/**
 * packageName    : com.example.coupon_con.application.port
 * fileName       : IssueCouponALLMemberUseCase
 * author         : JAEIK
 * date           : 9/2/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/2/25        JAEIK       최초 생성
 */
public interface IssueCouponAllMemberUseCase {
    void issueCouponAllMember(List<Member> memberId, Long couponId);
}
