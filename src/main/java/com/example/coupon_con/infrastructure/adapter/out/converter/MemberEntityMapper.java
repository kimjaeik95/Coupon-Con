package com.example.coupon_con.infrastructure.adapter.out.converter;

import com.example.coupon_con.domain.Member;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.converter
 * fileName       : MemberEntityMapper
 * author         : JAEIK
 * date           : 6/27/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/27/25       JAEIK       최초 생성
 */
@Component
public class MemberEntityMapper {
    // memberMybatisEntity -> member domain 변환
    public Member mapToMemberDomain(MemberMybatisEntity memberMybatisEntity) {
        return Member.builder()
                .memberId(memberMybatisEntity.getMemberId())
                .name(memberMybatisEntity.getName())
                .createdAt(memberMybatisEntity.getCreatedAt())
                .build();
    }

    // member domain ->  MemberMybatisEntity 변환
    public MemberMybatisEntity mapToMemberEntity(Member member) {
        return MemberMybatisEntity.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
