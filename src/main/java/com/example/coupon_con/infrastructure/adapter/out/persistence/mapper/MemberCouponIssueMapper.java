package com.example.coupon_con.infrastructure.adapter.out.persistence.mapper;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.CouponMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberCouponIssueMybatisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.persistence.mapper
 * fileName       : MemberCouponIssueMapper
 * author         : JAEIK
 * date           : 6/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/28/25       JAEIK       최초 생성
 */
@Mapper
public interface MemberCouponIssueMapper {
    int updateQuantityOnIssue(Long couponId);

    // Mybatis 내부적으로 Map 형태로 감싼다. (파라미터 타입에 두개를 넣어야하기때문에 parameterType="java.lang.Long" 단일값만)
    void insertMemberCouponIssue(@Param("memberId") Long memberId,@Param("couponId") Long couponId);
}
