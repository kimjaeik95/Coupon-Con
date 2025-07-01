package com.example.coupon_con.infrastructure.adapter.in.web;

import com.example.coupon_con.application.mapper.CouponDtoMapper;
import com.example.coupon_con.application.port.in.IssueCouponToMemberUseCase;
import com.example.coupon_con.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.in.web
 * fileName       : MemberCouponIssueController
 * author         : JAEIK
 * date           : 6/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/28/25       JAEIK       최초 생성
 */
@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class MemberCouponIssueController {
    private final IssueCouponToMemberUseCase issueCouponToMemberUseCase;
    private final CouponDtoMapper couponDtoMapper;

    @PostMapping("/issue")
    public ResponseEntity<?> issueCouponToMember(@RequestParam("memberId") Long memberId, @RequestParam("couponId") Long couponId) {
        Coupon coupon = issueCouponToMemberUseCase.issueCouponToMember(memberId, couponId);
        return ResponseEntity.ok().body(couponDtoMapper.toCouponResponseDto(coupon));
    }
}
