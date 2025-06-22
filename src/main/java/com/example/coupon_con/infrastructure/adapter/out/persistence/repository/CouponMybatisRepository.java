package com.example.coupon_con.infrastructure.adapter.out.persistence.repository;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.CouponMybatisEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.persistence.repository
 * fileName       : CouponMybatisRepository
 * author         : JAEIK
 * date           : 6/22/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/22/25       JAEIK       최초 생성
 */
@Mapper
public interface CouponMybatisRepository {
    @Insert("INSERT INTO coupon (coupon_name, coupon_number, quantity) VALUES (#{couponName}, #{couponNumber}, #{quantity})")
    void insert(CouponMybatisEntity entity);
}
