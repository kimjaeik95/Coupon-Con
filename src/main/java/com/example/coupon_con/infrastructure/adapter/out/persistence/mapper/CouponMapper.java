package com.example.coupon_con.infrastructure.adapter.out.persistence.mapper;

import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.CouponMybatisEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    void insert(CouponMybatisEntity coupon);
    List<CouponMybatisEntity> findAll();
    void deleteById(@Param("couponId") Long couponId);

    // myBatis 업데이트된 데이터 반환 기능 없다. JPA랑 다르다.
    int update(CouponMybatisEntity couponMybatisEntity);

    CouponMybatisEntity findById(@Param("couponId") Long couponId);
}
