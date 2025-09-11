package com.example.coupon_con.infrastructure.adapter.out.persistence.mapper;


import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.CouponMybatisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.persistence.mapper
 * fileName       : CouponMapper
 * author         : JAEIK
 * date           : 6/23/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/23/25       JAEIK       최초 생성
 */
@Mapper
public interface CouponMapper {
    // 쿠폰 생성
    void insert(CouponMybatisEntity coupon);

    // 쿠폰 전체 찾기
    List<CouponMybatisEntity> findAll();

    // 쿠폰 단건 찾기
    CouponMybatisEntity findById(@Param("couponId") Long couponId);

    // 쿠폰 이름으로 찾기
    CouponMybatisEntity findByName(@Param("couponName") String couponName);

    // 쿠폰Number 로 쿠폰 찾기
    CouponMybatisEntity findByCouponNumber(@Param("couponNumber") String couponNumber);

    // 쿠폰 비관적 락
    CouponMybatisEntity findByIdLock(@Param("couponId") Long couponId);

    // 쿠폰 단건 삭제
    void deleteById(@Param("couponId") Long couponId);

    // myBatis 업데이트된 데이트 후 객체 반환하기 없다. void or int 타입
    void update(CouponMybatisEntity couponMybatisEntity);

    // 쿠폰 수량 업데이트
    void updateQuantity(@Param("couponId") Long couponId, @Param("quantity") int quantity);

    // 직접 DB 에서 수량 차감, 제약조건 있는 업데이트
    int updateQuantityOnIssue(Long couponId);
}
