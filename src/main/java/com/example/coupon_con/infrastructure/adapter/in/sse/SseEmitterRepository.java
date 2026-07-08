package com.example.coupon_con.infrastructure.adapter.in.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.in.sse
 * fileName       : SseEmitterRepository
 * author         : JAEIK
 * date           : 7/7/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/7/26        JAEIK       최초 생성
 */
@Component
public class SseEmitterRepository {

    // 사용자 :emitter 메모리  1:1   / key : 문자열 , value : emitter 실제 객체 인스턴스
    // ConcurrentHashMap 동일 회원 여러스레드에서 들어와도 중복 처리방지
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 문자열로 사용하기편하게
    private String key(Long memberId, Long couponId) {
        return memberId + " : " + couponId;
    }

    public SseEmitter save(Long memberId, Long couponId, SseEmitter emitter) {
        emitters.put(key(memberId, couponId), emitter);
        return emitter;
    }

    // null 방지 key 로 map 에서 꺼냄
    public Optional<SseEmitter> get(Long memberId, Long couponId) {
        return Optional.ofNullable(emitters.get(key(memberId, couponId)));
    }

    public void remove(Long memberId, Long couponId) {
        emitters.remove(key(memberId, couponId));
    }
}
