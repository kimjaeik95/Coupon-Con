package com.example.coupon_con.infrastructure.config.batch;

import com.example.coupon_con.infrastructure.batch.writer.BatchInsertWriter;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.example.coupon_con.infrastructure.config.batch
 * fileName       : MemberCouponIssueWriterConfig
 * author         : JAEIK
 * date           : 9/19/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/19/25        JAEIK       최초 생성
 */
@Configuration
@RequiredArgsConstructor
public class MemberCouponIssueWriterConfig {

    private final SqlSession sqlSession;
    @Bean
    public BatchInsertWriter batchInsertWriter() {
        return new BatchInsertWriter(sqlSession);
    }
}

