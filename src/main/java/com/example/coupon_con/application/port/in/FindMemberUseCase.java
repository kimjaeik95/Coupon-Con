package com.example.coupon_con.application.port.in;

import com.example.coupon_con.domain.Member;

import java.util.Optional;

/**
 * packageName    : com.example.coupon_con.application.port.in
 * fileName       : FindMemberUseCase
 * author         : JAEIK
 * date           : 6/27/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/27/25       JAEIK       최초 생성
 */
public interface FindMemberUseCase {
    Member findMember(Long memberId);
}
