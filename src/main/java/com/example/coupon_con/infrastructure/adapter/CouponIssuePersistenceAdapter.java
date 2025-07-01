package com.example.coupon_con.infrastructure.adapter;

import com.example.coupon_con.application.port.out.IssueCouponToMemberPort;
import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.infrastructure.adapter.out.converter.CouponEntityMapper;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.CouponMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberCouponIssueMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.CouponMapper;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberCouponIssueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter
 * fileName       : CouponIssuePersistenceAdapter
 * author         : JAEIK
 * date           : 6/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/28/25       JAEIK       최초 생성
 */
@Component
@RequiredArgsConstructor
public class CouponIssuePersistenceAdapter implements IssueCouponToMemberPort {
    private final MemberCouponIssueMapper couponIssueMapper;
    private final CouponMapper couponMapper;
    private final CouponEntityMapper couponEntityMapper;
    @Override
    public Optional<Coupon> minusCouponQuantity(Long memberId, Long couponId) {
         // 1.업데이트문으로 쿠폰 발급 수량 차감
         int updateCoupon = couponIssueMapper.updateQuantityOnIssue(couponId);
         if (updateCoupon == 0) return Optional.empty();

         try {
             // 2.회원-쿠폰 발급 기록 테이블 Insert
             couponIssueMapper.insertMemberCouponIssue(memberId, couponId);
         } catch (DuplicateKeyException dke) {
             throw new IllegalArgumentException("이미 발급된 쿠폰입니다.");
         }

        // 3.발급쿠폰 조회 반환
        CouponMybatisEntity couponMybatis = couponMapper.findById(couponId);
        return Optional.ofNullable(couponMybatis).map(couponEntityMapper::mapToDomainEntity);
    }
}
