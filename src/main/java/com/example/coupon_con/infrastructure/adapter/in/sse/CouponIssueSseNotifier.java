package com.example.coupon_con.infrastructure.adapter.in.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.in.sse
 * fileName       : CouponIssueSseNotifier
 * author         : JAEIK
 * date           : 7/7/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/7/26        JAEIK       최초 생성
 */

@Component
@RequiredArgsConstructor
public class CouponIssueSseNotifier {
    private final SseEmitterRepository sseEmitterRepository;

    public void notifySuccess(Long memberId, Long couponId) {
        send(memberId, couponId, "coupon-issued", CouponIssueResult.success(couponId));
    }

    // 실패이유는 다양하므로 message 변수로 받기
    public void notifyFail(Long memberId, Long couponId, String reason) {
        send(memberId, couponId, "coupon-issued-fail", CouponIssueResult.fail(couponId, reason));
    }

    private void send(Long memberId, Long couponId, String eventName, Object data) {
        // null 아무것도 실행 안함
        sseEmitterRepository.get(memberId, couponId).ifPresent(emitter -> {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
                emitter.complete(); // 쿠폰 발급은 일회성 알림으로 send 완료 후 종료!
            } catch (IOException e) {
                emitter.completeWithError(e);
            } finally {
                sseEmitterRepository.remove(memberId, couponId);
            }
        });
    }

    //  성공 or 실패 응답 dto
    public record CouponIssueResult(Long couponId, boolean success, String message) {
        public static CouponIssueResult success(Long couponId) {
            return new CouponIssueResult(couponId, true, "쿠폰이 발급되었습니다.");
        }

        public static CouponIssueResult fail(Long couponId, String reason) {
            return new CouponIssueResult(couponId, false, reason);
        }
    }
}
