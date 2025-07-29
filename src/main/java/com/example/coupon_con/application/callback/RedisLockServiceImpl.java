package com.example.coupon_con.application.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.function.Supplier;

/**
 * packageName    : com.example.coupon_con.application.callback
 * fileName       : RedisLcokServiceImpl
 * author         : JAEIK
 * date           : 7/28/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/28/25       JAEIK       최초 생성
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisLockServiceImpl implements RedisLockService {
    private static final String REDIS_LOCK_PREFIX = "lock:coupon:";
    private final RedissonClient redissonClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public <T> T callWithLock(Long lockKey, Supplier<T> supplier, RedisLockTime lockTime) {
        String key = generateKey(lockKey);
        final RLock lock = redissonClient.getLock(key);
        return this.execute(supplier, lockTime, key, lock);
    }

    // callWithLock 전체공개가능한 Api 상세로직은 private로 관리
    private <T> T execute(final Supplier<T> supplier, RedisLockTime redisLockTime, final String key, final RLock lock) {
        try {
            log.info("lock 획득 시도: {}", key);
            if (lock.tryLock(redisLockTime.getWaitTime(), redisLockTime.getLeaseTime(), redisLockTime.getTimeUnit())) {
                log.info("lock 획득 성공: {}", key);
                return supplier.get();
            }
            // 락 획득실패에대한 예외처리
            throw new IllegalStateException("락 획득 실패");
            // 실 프로젝트라면 커스텀 인셉션으로 명확한 예외처리 해주는게 좋을거 같다.
        } catch (InterruptedException e) { // 스레드 작업 멈춤 (인터럽트 발생)
            log.error("lock 획득 실패: {}", key);
            throw new IllegalStateException("락 획득 중 인터러트 발생", e);
        } finally {
            // 이벤트 발행 파라미터 타입으로 스프링이 인식하기때문에  이벤트 발행 = 파라미터 타입객체 해야한다. (다른 이벤트는 다른 파라미터 객체 or 특정조건)
            applicationEventPublisher.publishEvent(new RedisLockEvent(key, lock));
        }
    }

    // 트랜잭션이 성공적으로 커밋되거나 롤백되었을 때 그 시점에 맞춰 이벤트 리스너를 처리하도록 등록
    // 직접 subscribeUnlock 이 아닌 트랜잭션 상태가 변화하면 스프링이 자동적으로 실행
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void subscribeUnlock(final RedisLockEvent lockEvent) {
        try {
            lockEvent.unlock();
            log.info("lock 해제 성공: {}", lockEvent.key);
        } catch (IllegalMonitorStateException e) {
            log.warn("이미 해제된 lock 입니다: {}", lockEvent.key);
        }
    }


    // record 불변데이터 간결하게 필드 + 생성자 + getter 생성
    // 재사용되지 않을거 같아 따로 클래스를 만들지 않았다.
    public record RedisLockEvent(String key, RLock lock) {
        public void unlock() {
            this.lock.unlock();
        }
    }


    private String generateKey(Long lockKey) {
        return REDIS_LOCK_PREFIX + lockKey;
    }
}

