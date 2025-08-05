package com.example.coupon_con.infrastructure;

import com.example.coupon_con.application.port.out.*;
import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.infrastructure.adapter.out.converter.CouponEntityMapper;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.CouponMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
@Slf4j
public class CouponPersistenceAdapter implements
        CreateCouponPort, GetAllCouponPort, DeleteCouponByIdPort,
        UpdateCouponPort, FindCouponPort, UpdateQuantityCouponPort {
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
    public Optional<Coupon> findById(Long couponId) {
        CouponMybatisEntity couponMybatisEntity = couponMapper.findById(couponId);
        // 변환매핑함수가 null 일경우 매핑함수가 호출되지 않아 혹시 모를 NPE 발생 가능성을 예방한다.
        // 사실 지금 서비스단에서 예외처리로 방지를하기 때문에 Null 이 매핑함수까지는 안오겠지만 혹시 모를 오류를위해 이중 처리 했다.
        return Optional.ofNullable(couponMybatisEntity).map(couponEntityMapper::mapToDomainEntity);

        //return Optional.ofNullable(couponEntityMapper.mapToDomainEntity(couponMybatisEntity));
    }

    @Override
    public Optional<Coupon> findByIdLock(Long couponId) {
        CouponMybatisEntity couponMybatisEntity = couponMapper.findByIdLock(couponId);
        return Optional.ofNullable(couponMybatisEntity).map(couponEntityMapper::mapToDomainEntity);
    }

    @Override
    public Optional<Coupon> findByCouponNUmber(String couponNumber) {
        CouponMybatisEntity couponMybatisEntity = couponMapper.findByCouponNumber(couponNumber);
        return Optional.ofNullable(couponMybatisEntity).map(couponEntityMapper::mapToDomainEntity);
    }

    @Override
    public void deleteById(Long couponId) {
        couponMapper.deleteById(couponId);
    }

    // Mybatis 는 update void or int 반환이라 객체를 반환하고싶으면 중복이지만 조회를 해야한다.
    @Override
    public Coupon update(Coupon coupon) {
        CouponMybatisEntity entity = couponEntityMapper.mapToMyBatisEntity(coupon);
        couponMapper.update(entity);
        CouponMybatisEntity updatedEntity = couponMapper.findById(entity.getCouponId());
        return couponEntityMapper.mapToDomainEntity(updatedEntity);
    }

    // 도메인에서 수량을 감소 했으니 파라미터를 도메인을 통해 받는다.
    @Override
    public void updateQuantity(Coupon coupon) {
        couponMapper.updateQuantity(coupon.getCouponId(), coupon.getQuantity());
        CouponMybatisEntity couponMybatisEntity = couponMapper.findById(coupon.getCouponId());
        couponEntityMapper.mapToDomainEntity(couponMybatisEntity);
    }

    // DB 내에서 수량을 감소하니 파라미터를 couponId로  받아도된다.
    @Override
    public Optional<Coupon> updateMinusCouponQuantity(Long couponId) {
        int updateQuantity = couponMapper.updateQuantityOnIssue(couponId);
        if (updateQuantity == 0) {
            throw new RuntimeException("쿠폰 수량이 부족 합니다.");
        }
        CouponMybatisEntity couponMybatisEntity = couponMapper.findById(couponId);
        return Optional.ofNullable(couponMybatisEntity).map(couponEntityMapper::mapToDomainEntity);
    }
}
