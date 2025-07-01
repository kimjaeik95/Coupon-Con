package com.example.coupon_con.infrastructure.adapter.in.web;

import com.example.coupon_con.application.mapper.MemberDtoMapper;
import com.example.coupon_con.application.port.in.FindMemberUseCase;
import com.example.coupon_con.application.port.in.dto.MemberResponse;
import com.example.coupon_con.domain.Member;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.in.web
 * fileName       : MemberController
 * author         : JAEIK
 * date           : 6/27/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/27/25       JAEIK       최초 생성
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final FindMemberUseCase findMemberUseCase;
    private final MemberDtoMapper memberDtoMapper;

    @GetMapping("/{memberId}")
    ResponseEntity<?> FindMembers(@PathVariable("memberId") Long memberId) {
        Member member = findMemberUseCase.findMember(memberId);
        return ResponseEntity.ok().body(memberDtoMapper.toMemberResponse(member));
    }
}
