package com.example.coupon_con.infrastructure.adapter.in.messaing;

import com.example.coupon_con.application.callback.RedisLockService;
import com.example.coupon_con.application.callback.RedisLockTime;
import com.example.coupon_con.application.port.in.dto.CouponIssueMessage;
import com.example.coupon_con.application.service.CouponIssueService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

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
@Slf4j
@RequiredArgsConstructor
public class CouponIssueMessageConsumer {
    private final RedisLockService redisLockService;
    private final CouponIssueService couponIssueService;
    private final MeterRegistry meterRegistry;

    @RabbitListener(queues = "${app.rabbitmq.coupon.queue}")
    public void consume(CouponIssueMessage message) {
        long publishedAt = message.getPublishedAt();

        // 큐 대기시간 발행 -> 컨슈머 처리
        long queueWaitMs = System.currentTimeMillis() - publishedAt;
        meterRegistry.timer("coupon.issue.queue.wait").record(queueWaitMs, TimeUnit.MILLISECONDS);

        Timer.Sample lockWaitSample = Timer.start(meterRegistry);

        try {
            redisLockService.callWithLock(
                    message.getCouponId(),
                    () -> {
                        lockWaitSample.stop(meterRegistry.timer("coupon.issue.lock.wait"));
                        Timer.Sample processSample = Timer.start(meterRegistry);
                        try {
                            couponIssueService.issueCouponNormally(message.getMemberId(), message.getCouponId());
                            // 실제 처리시간: 락 획득 -> DB 업데이트 완료
                            processSample.stop(Timer.builder("coupon.issue.process.duration")
                                    .tag("result", "success").register(meterRegistry));
                        } catch (Exception e) {
                            processSample.stop(Timer.builder("coupon.issue.process.duration")
                                    .tag("result", "fail").register(meterRegistry));
                            throw e;
                        }
                        return null;
                    },
                    RedisLockTime.LONG_LOCK);

            // 전체 완료시간 : 발행 -> 처리
            recordTotalDuration(publishedAt, "success");

        } catch (Exception e) {
            recordTotalDuration(publishedAt, "fail");
            log.error("쿠폰 발급 실패 memberId={}, couponId={}", message.getMemberId(), message.getCouponId(), e);
        }
    }

    // Total 집계  success , fail tag 구분
    private void recordTotalDuration(long publishedAt, String result) {
        long totalMs = System.currentTimeMillis() - publishedAt;
        meterRegistry.timer("coupon.issue.total.duration", "result", result)
                .record(totalMs, TimeUnit.MILLISECONDS);
    }
}
