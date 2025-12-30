package com.example.coupon_con.infrastructure.batch.processor;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberCouponIssueMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * packageName    : com.example.coupon_con.infrastructure.batch.processor
 * fileName       : MemberCouponProcessor
 * author         : JAEIK
 * date           : 9/8/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/8/25        JAEIK       최초 생성
 */
@Component
@StepScope
public class MemberCouponIssueProcessor implements ItemProcessor<MemberMybatisEntity, MemberCouponIssueMybatisEntity> {
    // Processor → 읽은 데이터를 가공/변환 (순수 Java 코드)
    /*
     1. Reader 에서 넘어온 Member 를 받는다.
     2. couponId jobParameter 받는다.
     3. 발급 정보 설정
     5. memberId, couponId로  MemberCouponIssue 객체생성  -> writer 객체 반환
     */
    private final Long couponId;

    // @RequiredArgsConstructor 사용 불가 특수한 주입(@Value("#{jobParameters['couponId']}")) 처리
    public MemberCouponIssueProcessor(@Value("#{jobParameters['couponId']}") Long couponId) {
        this.couponId = couponId;
    }

    @Override
    public MemberCouponIssueMybatisEntity process(MemberMybatisEntity item) throws Exception {

        return MemberCouponIssueMybatisEntity.builder()
                .couponId(couponId)
                .memberId(item.getMemberId())
                .issuedAt(Instant.now())
                .build();

    }
}
