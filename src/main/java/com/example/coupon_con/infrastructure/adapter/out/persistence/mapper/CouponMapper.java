package com.example.coupon_con.infrastructure.adapter.out.persistence.mapper;

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
}
