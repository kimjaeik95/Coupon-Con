package com.example.coupon_con.infrastructure.adapter.in.messaing;

import com.example.coupon_con.application.port.in.dto.CouponIssueMessage;
import com.example.coupon_con.application.port.out.IssueCouponToMemberPort;
import com.example.coupon_con.domain.MemberCouponIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.in.messaing
 * fileName       : CouponIssueMessageConsumer
 * author         : JAEIK
 * date           : 7/2/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/2/26        JAEIK       최초 생성
 */
@Component
@RequiredArgsConstructor
public class CouponIssueMessageConsumer {
    private final IssueCouponToMemberPort issueCouponToMemberPort;

    @RabbitListener(queues = "#{@couponRabbitProperties.queue}")
    public void consume(CouponIssueMessage message) {
        MemberCouponIssue memberCouponIssue = MemberCouponIssue.forIssue(message.getMemberId(), message.getCouponId());
        issueCouponToMemberPort.saveMemberCouponIssue(memberCouponIssue);
    }
}
