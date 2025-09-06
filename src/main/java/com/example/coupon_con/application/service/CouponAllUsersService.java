package com.example.coupon_con.application.service;

import com.example.coupon_con.application.port.in.IssueCouponAllMemberUseCase;
import com.example.coupon_con.application.port.out.FindCouponPort;
import com.example.coupon_con.application.port.out.FindMemberPort;
import com.example.coupon_con.application.port.out.IssueCouponAllMemberPort;
import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.coupon_con.application.service
 * fileName       : CouponAllUsersService
 * author         : JAEIK
 * date           : 9/2/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/2/25        JAEIK       최초 생성
 */
//@Service
//@RequiredArgsConstructor
//public class CouponAllUsersService implements IssueCouponAllMemberUseCase {
//    private final FindCouponPort findCouponPort;
//    private final FindMemberPort findMemberPort;
////    private final IssueCouponAllMemberPort issueCouponAllMemberPort;
//    @Override
//    public void issueCouponAllMember(List<Member> memberId, Long couponId) {
//        /* todo : 1. 쿠폰 조회 , 수량확인까지  or 쿠폰생성까지
//                  2. 멤버 전체조회로 멤버 수 확인 배치로 가져와야 한다.  배치 +  페이징 or 커서
//                  3. 쿠폰멤버Issue 테이블 배치 저장 배치 + 페이징 or 커서
//         */
//        int batchSize = 10000;
//
//        Coupon coupon = findCouponPort.findById(couponId)
//                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다."));
//
//        // 멤버 전체조회
//        List<Member> member = findMemberPort.findByAllMember();
//
//
//        // 수량 차감 + 예외처리
//        coupon.decreaseQuantity();
//
//    }
//}
