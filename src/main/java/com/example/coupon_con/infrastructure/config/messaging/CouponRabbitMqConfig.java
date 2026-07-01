package com.example.coupon_con.infrastructure.config.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.example.coupon_con.infrastructure.config.messaging
 * fileName       : RabbitMqConfig
 * author         : JAEIK
 * date           : 7/1/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/1/26        JAEIK       최초 생성
 */
@Configuration
public class CouponRabbitMqConfig {
    public static final String COUPON_ISSUE_EXCHANGE = "coupon.issue.exchange";
    public static final String COUPON_ISSUE_QUEUE = "coupon.issue.queue";
    public static final String COUPON_ISSUE_ROUTING_KEY = "coupon.issue";

    // 단일 큐이기 때문에 DirectExchange 설정
    @Bean
    public DirectExchange couponIssueExchange() {
        return new DirectExchange(COUPON_ISSUE_EXCHANGE);
    }

    @Bean
    public Queue couponIssueQueue() {
        // durable=true  RabbitMQ 재시작시 큐는 살아있지만 메시지는 유실되므로 메시지는  persistent 설정필요
        return new Queue(COUPON_ISSUE_QUEUE, true);
    }

    // couponIssueExchange 들어온 메시지가  routingKey  일치하는 Queue 보낸다 (바인딩)
    @Bean
    public Binding couponIssueBinding(Queue couponIssueQueue, DirectExchange couponIssueExchange) {
        return BindingBuilder.bind(couponIssueQueue)
                .to(couponIssueExchange)
                .with(COUPON_ISSUE_ROUTING_KEY);
    }

    // 매번 직렬화/역직렬화 코드를 작성해야 하는 번거로움 해결해줌
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /*  기본 컨버터 SimpleMessageConverter 입니다.  POJO 객체를 JSON 으로 변환하지 못한다.
        Jackson2JsonMessageConverter 자동으로 변환해준다.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }
}

