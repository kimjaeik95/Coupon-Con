package com.example.coupon_con.infrastructure.adapter.in.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.in.sse
 * fileName       : CouponSubscribeContorller
 * author         : JAEIK
 * date           : 7/7/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/7/26        JAEIK       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class CouponSubscribeController {
    // 10분 동안 이벤트가 없으면 연결을 종료
    private static final Long DEFAULT_TIMEOUT = 10L * 60 * 1000;
    private final SseEmitterRepository sseEmitterRepository;

    // SSE 응답을 위해 Content-Type: text/event-stream 설정
    @GetMapping(value = "/subscribe/{couponId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable("couponId") Long couponId, @RequestParam("memberId") Long memberId) {

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        sseEmitterRepository.save(memberId, couponId, emitter);

        // onCompletion : 정상완료시 제거
        emitter.onCompletion(() -> sseEmitterRepository.remove(memberId, couponId));

        // onTimeout/onError 각자의 상황에 맞게 종료를 트리거함
        // onCompletion 자동으로 호출되어 위 remove()가 실행
        emitter.onTimeout(emitter::complete);
        emitter.onError(emitter::completeWithError);

        // 연결 직후 더비 이벤트 전송 (정상 연결 되었는지 확인용 없을경우 오류터질 위험)
        try {
            emitter.send(SseEmitter.event().name("connect").data("ok"));
        } catch (IOException e) {
            sseEmitterRepository.remove(memberId, couponId);
        }

        return emitter;
    }
}
