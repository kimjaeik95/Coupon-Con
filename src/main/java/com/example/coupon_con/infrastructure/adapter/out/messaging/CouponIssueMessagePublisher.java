package com.example.coupon_con.infrastructure.adapter.out.messaging;

import com.example.coupon_con.application.port.in.dto.CouponIssueMessage;
import com.example.coupon_con.infrastructure.properties.CouponRabbitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.out.messaging
 * fileName       : CouponIssueMessagePulisher
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
public class CouponIssueMessagePublisher {
    private final RabbitTemplate rabbitTemplate;
    private final CouponRabbitProperties properties;

    // Producer (Publisher)  Exchange 메시지 전송  Routing Key 매칭
    public void publish(CouponIssueMessage message) {
        rabbitTemplate.convertAndSend(
                properties.getExchange(),
                properties.getRoutingKey(),
                message
        );
    }
}
