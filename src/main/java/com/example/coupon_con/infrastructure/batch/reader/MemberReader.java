package com.example.coupon_con.application.config.batach;

import com.example.coupon_con.domain.Member;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * packageName    : com.example.coupon_con.infrastructure.batach
 * fileName       : CouponJob
 * author         : JAEIK
 * date           : 9/8/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/8/25        JAEIK       최초 생성
 */
@RequiredArgsConstructor
@Component
public class IssueMemberCouponJob implements ItemReader<MemberMybatisEntity> {
    /*
     1. 쿠폰 조회
     2. 회원 전체조회
     3. 멤버-쿠폰 중간테이블 insert
     job _ step _ chunk 구성
     */
    private final MemberMapper memberMapper;
    private List<MemberMybatisEntity> members;
    @Override
    public Member read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (members == null) {
            members = memberMapper.findAllMembers();
        }

        if ()

        return null;
    }


}
