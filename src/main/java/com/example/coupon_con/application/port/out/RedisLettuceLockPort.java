package com.example.coupon_con.application.port.out;

/**
 * packageName    : com.example.coupon_con.application.port.out
 * fileName       : RedisLettuceLockPort
 * author         : JAEIK
 * date           : 7/18/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/18/25       JAEIK       최초 생성
 */
public interface RedisLettuceLockPort {
    boolean lock(Long key, String lockId);
    boolean unlock(Long key, String lockId);
}
