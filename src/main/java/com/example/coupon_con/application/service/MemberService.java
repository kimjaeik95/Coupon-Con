package com.example.coupon_con.application.service;

import com.example.coupon_con.application.port.in.FindMemberUseCase;
import com.example.coupon_con.application.port.out.FindMemberPort;
import com.example.coupon_con.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName    : com.example.coupon_con.application.service
 * fileName       : MemberService
 * author         : JAEIK
 * date           : 6/27/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/27/25       JAEIK       최초 생성
 */
@Service
@RequiredArgsConstructor
public class MemberService implements FindMemberUseCase {
    private final FindMemberPort findMemberPort;
    @Override
    public Member findMember(Long memberId) {
        return findMemberPort.findById(memberId)
                .orElseThrow(()-> new IllegalArgumentException("멤버를 찾을 수 없습니다."));
    }
}
