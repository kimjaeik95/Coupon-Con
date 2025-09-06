package com.example.coupon_con.application.service;

import com.example.coupon_con.application.port.in.IssueCouponToMemberUseCase;
import com.example.coupon_con.application.port.in.UseCouponUseCase;
import com.example.coupon_con.application.port.in.command.UseCouponCommand;
import com.example.coupon_con.application.port.in.dto.UseCouponResponse;
import com.example.coupon_con.application.port.out.*;
import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.domain.MemberCouponIssue;
import lombok.RequiredArgsConstructor;
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
public class CouponIssueService implements IssueCouponToMemberUseCase, UseCouponUseCase {
    private final IssueCouponToMemberPort issueCouponToMemberPort;
    private final FindMemberPort findMemberPort;
    private final FindCouponPort findCouponPort;
    private final UpdateQuantityCouponPort updateQuantityCouponPort;
    private final FindIssueCouponPort findIssueCouponPort;
    private final UpdateIssueCouponPort updateIssueCouponPort;

    // 원자적쿼리
    @Override
    public Coupon issueCouponWithAtomicDbUpdate(Long memberId, Long couponId) {
        // 발급 전 회원 존재 확인
        findMemberPort.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        // 발급 전 쿠폰 존재 확인
        Coupon coupon = updateQuantityCouponPort.updateMinusCouponQuantity(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        // 쿠폰 발급 테이블 Insert
        MemberCouponIssue memberCouponIssue = new MemberCouponIssue(memberId, couponId);
        issueCouponToMemberPort.saveMemberCouponIssue(memberCouponIssue);
        return coupon;
    }

    // 비관적 락
    @Override
    @Transactional
    public Coupon issueCouponWithPessimisticLock(Long memberId, Long couponId) {
        // 비관적 락 FOR UPDATE 쿼리 실행 위치
        findMemberPort.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        Coupon coupon = findCouponPort.findByIdLock(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다."));

        // 도메인 쿠폰 -1 차감
        coupon.decreaseQuantity();

        updateQuantityCouponPort.updateQuantity(coupon);


        MemberCouponIssue memberCouponIssue = new MemberCouponIssue(memberId, couponId);
        issueCouponToMemberPort.saveMemberCouponIssue(memberCouponIssue);

        return coupon;
    }

    // Facade 패턴과 Call 패턴을 위한 기본 서비스 로직
    @Override
    public Coupon issueCouponNormally(Long memberId, Long couponId) {
        findMemberPort.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다."));

        Coupon coupon = findCouponPort.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        coupon.decreaseQuantity();

        updateQuantityCouponPort.updateQuantity(coupon);

        MemberCouponIssue couponIssue = new MemberCouponIssue(memberId, couponId);

        issueCouponToMemberPort.saveMemberCouponIssue(couponIssue);

        return coupon;
    }

    // 쿠폰 사용
    // 로그인 기능이 없으므로 인증 객체에서 memberId가 아닌 memberId 직접 파라미터로 받는다.
    @Override
    public UseCouponResponse UseCoupon(Long memberId, UseCouponCommand useCouponCommand) {
        // CouponNumber 쿠폰찾기
        Coupon coupon = findCouponPort.findByCouponNumber(useCouponCommand.getCouponNumber())
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다."));

        // 멤버Id, 쿠폰Id 쿠폰 발급 이력 찾기
        MemberCouponIssue couponIssue = findIssueCouponPort.findIssueCoupon(memberId, coupon.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("발급된 쿠폰이 없습니다."));

        // 사용 유무 체크
        couponIssue.checkUsed();

        // 사용 Used, UsedAt 업데이트
        couponIssue.updateUsedCoupon();
        updateIssueCouponPort.updateUsedStatus(couponIssue);

        return UseCouponResponse.fromResponse(coupon.getCouponName(), couponIssue.getUsedAt());
    }
}
