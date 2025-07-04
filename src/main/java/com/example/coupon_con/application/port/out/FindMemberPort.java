package com.example.coupon_con.application.port.out;

import com.example.coupon_con.domain.Member;

import java.util.Optional;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : FindMemberPort
 * author         : JAEIK
 * date           : 6/27/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/27/25       JAEIK       최초 생성
 */
public interface FindMemberPort {
    Optional<Member> findById(Long memberId);
}
