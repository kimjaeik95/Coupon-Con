package com.example.coupon_con.application.service;

import com.example.coupon_con.application.port.in.IssueCouponToMemberUseCase;
import com.example.coupon_con.application.port.out.FindMemberPort;
import com.example.coupon_con.application.port.out.IssueCouponToMemberPort;
import com.example.coupon_con.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName    : com.example.coupon_con.application.service
 * fileName       : CouponIssueServcie
 * author         : JAEIK
 * date           : 6/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/28/25       JAEIK       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CouponIssueService implements IssueCouponToMemberUseCase {
    private final IssueCouponToMemberPort issueCouponToMemberPort;
    private final FindMemberPort findMemberPort;
    @Override
    public Coupon issueCouponToMember(Long memberId, Long couponId) {
        // 발급 전 회원 존재 확인
        findMemberPort.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        // 발급 전 쿠폰 존재 확인
        Coupon coupon = issueCouponToMemberPort.minusCouponQuantity(memberId,couponId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        return coupon;
    }
}
