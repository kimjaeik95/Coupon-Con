package com.example.coupon_con.application.callback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * packageName    : com.example.coupon_con.application.callback
 * fileName       : RedisLockTime
 * author         : JAEIK
 * date           : 7/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/28/25       JAEIK       최초 생성
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RedisLockTime {
    // 서비스 마다 락 정책이나 락 대상이 다르면
    private long waitTime;  // 락 획득 시도 기다리는 최대 시간
    private long leaseTime; // TTL
    private TimeUnit timeUnit; // 시간 단위


    // 상수로 만들어놓는게 더 효울적이다  클래스 로드시 1회 생성 / 메서드 호출할때마다 생성
    public static final RedisLockTime DEFAULT = new RedisLockTime(5, 3, TimeUnit.SECONDS);
    public static final RedisLockTime SHORT_LOCK = new RedisLockTime(1, 2, TimeUnit.SECONDS);
    public static final RedisLockTime LONG_LOCK = new RedisLockTime(10, 30, TimeUnit.SECONDS);

}
