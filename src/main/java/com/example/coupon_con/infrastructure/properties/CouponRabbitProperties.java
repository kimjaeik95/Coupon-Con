package com.example.coupon_con.infrastructure.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * packageName    : com.example.coupon_con.infrastructure.properties
 * fileName       : CouponRabbitProperties
 * author         : JAEIK
 * date           : 7/2/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/2/26        JAEIK       최초 생성
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "app.rabbitmq.coupon")
public class CouponRabbitProperties {
    private String exchange;
    private String queue;
    private String routingKey;

}
