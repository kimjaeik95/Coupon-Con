package com.example.coupon_con.infrastructure.adapter.out.converter;

import com.example.coupon_con.domain.MemberCouponIssue;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberCouponIssueMybatisEntity;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.converter
 * fileName       : CouponIssueEntityMapper
 * author         : JAEIK
 * date           : 6/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/28/25       JAEIK       최초 생성
 */
@Component
public class CouponIssueEntityMapper {
    // Mybatis - > Domain
    public MemberCouponIssue mapToCouponIssueDomain(MemberCouponIssueMybatisEntity memberCouponIssueMybatisEntity) {
        return MemberCouponIssue.builder()
                .couponIssueId(memberCouponIssueMybatisEntity.getCouponIssueId())
                .couponId(memberCouponIssueMybatisEntity.getCouponId())
                .memberId(memberCouponIssueMybatisEntity.getMemberId())
                .issuedAt(memberCouponIssueMybatisEntity.getIssuedAt())
                .used(memberCouponIssueMybatisEntity.getUsed())
                .usedAt(memberCouponIssueMybatisEntity.getUsedAt())
                .build();
    }

    // Domain -> Mybatis
    public MemberCouponIssueMybatisEntity mapToCouponIssueEntity(MemberCouponIssue memberCouponIssue) {
        return MemberCouponIssueMybatisEntity.builder()
                .couponIssueId(memberCouponIssue.getCouponIssueId())
                .couponId(memberCouponIssue.getCouponId())
                .memberId(memberCouponIssue.getMemberId())
                .issuedAt(memberCouponIssue.getIssuedAt())
                .used(memberCouponIssue.getUsed())
                .usedAt(memberCouponIssue.getUsedAt())
                .build();
    }
}
