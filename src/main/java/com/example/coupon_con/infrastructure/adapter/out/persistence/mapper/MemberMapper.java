package com.example.coupon_con.infrastructure.adapter.out.persistence.mapper;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.persistence.mapper
 * fileName       : MemberMapper
 * author         : JAEIK
 * date           : 6/27/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/27/25       JAEIK       최초 생성
 */
@Mapper
public interface MemberMapper {
    MemberMybatisEntity findById(Long memberId);

    List<MemberMybatisEntity> findAll();

    List<MemberMybatisEntity> findAllPaged(@Param("lastId") Long lastId,
                                      @Param("pageSize") int pageSize,
                                           @Param("couponId")Long couponId);
}
