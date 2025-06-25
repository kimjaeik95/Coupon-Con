package com.example.coupon_con.infrastructure.adapter.out.persistence.repository;

import com.example.coupon_con.application.port.out.CreateCouponPort;
import com.example.coupon_con.application.port.out.DeleteCouponByIdPort;
import com.example.coupon_con.application.port.out.GetAllCouponPort;
import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.infrastructure.adapter.out.converter.CouponEntityMapper;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.CouponMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
public class CouponPersistenceAdapter implements CreateCouponPort, GetAllCouponPort, DeleteCouponByIdPort {
    private final CouponEntityMapper couponEntityMapper;
    private final CouponMapper couponMapper;

    @Override
    public void save(Coupon coupon) {
        couponMapper.insert(couponEntityMapper.mapToMyBatisEntity(coupon));
    }

    @Override
    public List<Coupon> findAll() {
        List<CouponMybatisEntity> entities = couponMapper.findAll();
        return entities.stream()
                .map(couponEntityMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long couponId) {
        couponMapper.deleteById(couponId);
    }
}
