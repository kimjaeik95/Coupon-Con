package com.example.coupon_con.infrastructure;

import com.example.coupon_con.application.port.out.IssueCouponToMemberPort;
import com.example.coupon_con.domain.MemberCouponIssue;
import com.example.coupon_con.infrastructure.adapter.out.converter.CouponIssueEntityMapper;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberCouponIssueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
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
        // DB 예외는  마이바티스가 JPA처럼 서비스까지 자동 예외 변환이 안 된다. 어댑터쪽에서 처리
        // AOP(Aspect-Oriented Programming) JPA
        try {
            // 쿠폰 발급 이력 멤버 ID , 쿠폰 ID INSERT
            couponIssueMapper.insertMemberCouponIssue(couponIssueEntityMapper.mapToCouponIssueEntity(memberCouponIssue));
        } catch (DataAccessException e) {
            throw new DuplicateKeyException("쿠폰 중복입니다.");
        }
    }
}
