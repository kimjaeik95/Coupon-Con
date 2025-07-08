package com.example.coupon_con.infrastructure.adapter;

import com.example.coupon_con.application.port.out.IssueCouponToMemberPort;
import com.example.coupon_con.domain.MemberCouponIssue;
import com.example.coupon_con.infrastructure.adapter.out.converter.CouponIssueEntityMapper;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberCouponIssueMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberCouponIssueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


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
    private final CouponIssueEntityMapper couponIssueEntityMapper;

    @Override
    public void saveMemberCouponIssue(MemberCouponIssue memberCouponIssue) {
        // 쿠폰 발급 이력 멤버 ID , 쿠폰 ID INSERT
        couponIssueMapper.insertMemberCouponIssue(couponIssueEntityMapper.mapToCouponIssueEntity(memberCouponIssue));
    }
}
