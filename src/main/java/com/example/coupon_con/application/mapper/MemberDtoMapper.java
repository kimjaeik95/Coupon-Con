package com.example.coupon_con.application.mapper;

import com.example.coupon_con.application.port.in.dto.MemberResponse;
import com.example.coupon_con.domain.Member;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.application.mapper
 * fileName       : MemberDtoMapper
 * author         : JAEIK
 * date           : 6/27/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/27/25       JAEIK       최초 생성
 */
@Component
public class MemberDtoMapper {

    //  member domain ->  MemberResponse 변환
    public MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
