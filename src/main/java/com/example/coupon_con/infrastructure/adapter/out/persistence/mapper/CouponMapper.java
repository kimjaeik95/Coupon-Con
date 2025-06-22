package com.example.coupon_con.infrastructure.adapter.out.persistence.mapper;

import com.example.coupon_con.domain.Coupon;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.CouponMybatisEntity;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.persistence.mapper
 * fileName       : CouponMapper
 * author         : JAEIK
 * date           : 6/22/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/22/25       JAEIK       최초 생성
 */
@Component
public class CouponMapper {
    /*
      도메인 <->  Persistence(영속성) Entity 변환을 위해
      MVC 패턴은 엔티티클래스 하나가 도메인 모델 역할까지 수행하므로 DTO - 엔티티로 매핑한다.
      지금 이 프로젝트에서도 Mybatis 를 사용해서 도메인이랑 Persistence Entity 가 구조상 거의 동일하더라도
      명확한 관심사 분리와 유지보수성을 위해 두 객체를 분리 하였다..
     */

    // DB에서 조회한 Mybatis 엔티티  -> 도메인 객체 변환
    public Coupon mapToDomainEntity(CouponMybatisEntity couponMybatisEntity) {
        return Coupon.builder()
                .couponId(couponMybatisEntity.getCouponId())
                .couponName(couponMybatisEntity.getCouponName())
                .couponNumber(couponMybatisEntity.getCouponNumber())
                .couponCreateAt(couponMybatisEntity.getCouponCreateAt())
                .couponExpiredAt(couponMybatisEntity.getCouponExpireAt())
                .quantity(couponMybatisEntity.getQuantity())
                .isDeleted(couponMybatisEntity.getIsDeleted())
                .build();
    }

    // 도메인 객체 -> DB 저장용 Mybatis 엔티티 변환
    public CouponMybatisEntity mapToMyBatisEntity(Coupon coupon) {
        return CouponMybatisEntity.builder()
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .couponNumber(coupon.getCouponNumber())
                .couponCreateAt(coupon.getCouponCreateAt())
                .couponExpireAt(coupon.getCouponCreateAt())
                .quantity(coupon.getQuantity())
                .isDeleted(coupon.getIsDeleted())
                .build();
    }
}
