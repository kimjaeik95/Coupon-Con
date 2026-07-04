package com.example.coupon_con.infrastructure.adapter.in.messaing;

import com.example.coupon_con.application.callback.RedisLockService;
import com.example.coupon_con.application.callback.RedisLockTime;
import com.example.coupon_con.application.port.in.dto.CouponIssueMessage;
import com.example.coupon_con.application.service.CouponIssueService;
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
    private final RedisLockService redisLockService;
    private final CouponIssueService couponIssueService;

    @RabbitListener(queues = "${app.rabbitmq.coupon.queue}")
    public void consume(CouponIssueMessage message) {
        redisLockService.callWithLock(
                message.getCouponId(),
                () -> couponIssueService.issueCouponNormally(message.getMemberId(), message.getCouponId()),
                RedisLockTime.LONG_LOCK);
    }
}
