package com.example.coupon_con.infrastructure.adapter.in.web;

import com.example.coupon_con.application.mapper.CouponDtoMapper;
import com.example.coupon_con.application.mapper.UseCouponMapper;
import com.example.coupon_con.application.port.in.IssueCouponToMemberUseCase;
import com.example.coupon_con.application.port.in.UseCouponUseCase;
import com.example.coupon_con.application.port.in.dto.UseCouponRequest;
import com.example.coupon_con.application.port.in.dto.UseCouponResponse;
import com.example.coupon_con.application.service.LettuceLockFacade;
import com.example.coupon_con.application.service.RedissonLockFacade;
import com.example.coupon_con.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final UseCouponUseCase useCouponUseCase;
    private final LettuceLockFacade lettuceLockFacade;
    private final RedissonLockFacade redissonLockFacade;
    private final CouponDtoMapper couponDtoMapper;
    private final UseCouponMapper useCouponMapper;

    // DB 에서 수량 직접감소 후 발급
    @PostMapping("/issue")
    public ResponseEntity<?> issueCouponAtomic(@RequestParam("memberId") Long memberId, @RequestParam("couponId") Long couponId) {
        Coupon coupon = issueCouponToMemberUseCase.issueCouponWithAtomicDbUpdate(memberId, couponId);
        return ResponseEntity.ok().body(couponDtoMapper.toCouponResponseDto(coupon));
    }
    // 서비스로직에서 수량 감소 후 발급
    @PostMapping("/issue/pessimistic")
    public ResponseEntity<?> issueCouponPessimisticLock(@RequestParam("memberId") Long memberId, @RequestParam("couponId") Long couponId) {
        Coupon coupon = issueCouponToMemberUseCase.issueCouponWithPessimisticLock(memberId, couponId);
        return ResponseEntity.ok().body(couponDtoMapper.toCouponResponseDto(coupon));
    }

    // redis Lettuce
    @PostMapping("/issue/lettuce")
    public ResponseEntity<?> issueCouponLettuceLock(@RequestParam("memberId") Long memberId, @RequestParam("couponId") Long couponId) throws InterruptedException {
        Coupon coupon = lettuceLockFacade.issueCouponWithLettuceLock(memberId, couponId);
        return ResponseEntity.ok().body(couponDtoMapper.toCouponResponseDto(coupon));
    }

    // redis Redisson
    @PostMapping("/issue/redisson")
    public ResponseEntity<?> issueCouponRedissonLock(@RequestParam("memberId") Long memberId, @RequestParam("couponId") Long couponId) {
        Coupon coupon = redissonLockFacade.issueCouponWithRedissonLock(memberId, couponId);
        return ResponseEntity.ok().body(couponDtoMapper.toCouponResponseDto(coupon));
    }

    @PostMapping("/issue/callback")
    public ResponseEntity<?> issueCouponCallBackRedissonLock(@RequestParam("memberId") Long memberId, @RequestParam("couponId") Long couponId) {
        Coupon coupon = redissonLockFacade.issueCouponWithCallBackRedissonLock(memberId, couponId);
        return ResponseEntity.ok().body(couponDtoMapper.toCouponResponseDto(coupon));
    }

    // 쿠폰사용
    @PostMapping("/issue/used")
    public ResponseEntity<?> usedIssueCoupon(@RequestParam("memberId") Long memberId, @RequestBody UseCouponRequest useCouponRequest) {
        UseCouponResponse useCouponResponse = useCouponUseCase.UseCoupon(memberId, useCouponMapper.mapToCommand(useCouponRequest));
        return ResponseEntity.ok().body(useCouponResponse);
    }
}
