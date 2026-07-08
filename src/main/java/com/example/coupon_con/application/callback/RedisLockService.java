package com.example.coupon_con.application.callback;

import java.util.function.Supplier;

/**
 * packageName    : com.example.coupon_con.application.callback
 * fileName       : RedisLockService
 * author         : JAEIK
 * date           : 7/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/28/25       JAEIK       최초 생성
 */
public interface RedisLockService {

    //  RedisLockServiceImpl 구현할때 호출 메서드
    <T> T callWithLock(Long lockKey, Supplier<T> supplier, RedisLockTime redisLockTime);

    // 락 시간을 설정하지 않아도 되는 디폴트 메서드 서비스 클래스에서 호출
    // default 인터페이스여도 기본메서드처럼 반환가능
    default  <T> T callWithLock(Long lockKey, Supplier<T> supplier) {
        return this.callWithLock(lockKey, supplier, RedisLockTime.DEFAULT);
    }
}
