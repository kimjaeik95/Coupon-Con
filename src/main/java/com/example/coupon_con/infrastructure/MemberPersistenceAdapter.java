package com.example.coupon_con.infrastructure;

import com.example.coupon_con.application.port.out.FindMemberPort;
import com.example.coupon_con.domain.Member;
import com.example.coupon_con.infrastructure.adapter.out.converter.MemberEntityMapper;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter
 * fileName       : MemberPersistenceAdapter
 * author         : JAEIK
 * date           : 6/27/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/27/25       JAEIK       최초 생성
 */
@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements FindMemberPort {
    private final MemberEntityMapper entityMapper;
    private final MemberMapper memberMapper;
    @Override
    public Optional<Member> findById(Long memberId) {
        MemberMybatisEntity memberMybatis = memberMapper.findById(memberId);
        return Optional.ofNullable(memberMybatis).map(entityMapper::mapToMemberDomain);
    }

//    @Override
//    public List<Member> findByAllMember() {
//        List<MemberMybatisEntity> memberMybatis = memberMapper.findAllMembers();
//        return memberMybatis.stream()
//                .map(entityMapper::mapToMemberDomain)
//                .collect(Collectors.toList());
//    }
}
