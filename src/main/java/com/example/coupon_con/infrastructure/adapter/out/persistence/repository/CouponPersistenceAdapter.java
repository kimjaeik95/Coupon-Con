package com.example.coupon_con.infrastructure.adapter.out.persistence.repository;

import com.example.coupon_con.application.port.out.CreateCouponPort;
import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.persistence.repository
 * fileName       : MyBatisCouponRepository
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
@RequiredArgsConstructor
@Component
public class CouponPersistenceAdapter implements CreateCouponPort {
    private final CouponMapper couponMapper;
    private final CouponMybatisRepository couponMybatisRepository;

    @Override
    public void save(Coupon coupon) {
        couponMybatisRepository.insert(couponMapper.mapToMyBatisEntity(coupon));
    }
}
