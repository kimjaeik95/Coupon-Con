package com.example.coupon_con.application.service;

import com.example.coupon_con.application.port.in.IssueCouponToMemberUseCase;
import com.example.coupon_con.application.port.out.*;
import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.domain.MemberCouponIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final FindCouponPort findCouponPort;
    private final UpdateQuantityCouponPort updateQuantityCouponPrt;
    @Override
    public Coupon issueCouponWithAtomicDbUpdate(Long memberId, Long couponId) {
        // 발급 전 회원 존재 확인
        findMemberPort.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        // 발급 전 쿠폰 존재 확인
        Coupon coupon = updateQuantityCouponPrt.updateMinusCouponQuantity(couponId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        // 쿠폰 발급 테이블 Insert
        MemberCouponIssue memberCouponIssue = new MemberCouponIssue(memberId, couponId);
        issueCouponToMemberPort.saveMemberCouponIssue(memberCouponIssue);
        return coupon;
    }


    @Override
    @Transactional
    public Coupon issueCouponWithDomainLogic(Long memberId, Long couponId) {
            findMemberPort.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

            Coupon coupon = findCouponPort.findById(couponId)
                    .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다."));

            // 도메인 쿠폰 -1 차감
            coupon.decreaseQuantity();

        try {
            updateQuantityCouponPrt.updateQuantity(coupon);
        } catch (OptimisticLockingFailureException e) {
            throw new RuntimeException("쿠폰 발급 중 다른 사용자가 먼저 발급했습니다. 다시 시도해주세요.");
        }

            MemberCouponIssue memberCouponIssue = new MemberCouponIssue(memberId, couponId);
            issueCouponToMemberPort.saveMemberCouponIssue(memberCouponIssue);

            return coupon;
    }
}
