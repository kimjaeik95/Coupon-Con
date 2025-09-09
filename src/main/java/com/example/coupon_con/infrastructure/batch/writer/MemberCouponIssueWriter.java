package com.example.coupon_con.infrastructure.batch.writer;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberCouponIssueMybatisEntity;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.example.coupon_con.infrastructure.batch.writer
 * fileName       : MemberCouponIssueWriter
 * author         : JAEIK
 * date           : 9/9/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/9/25        JAEIK       최초 생성
 */
@Configuration
public class MemberCouponIssueWriter {

    @Bean
    public MyBatisBatchItemWriter<MemberCouponIssueMybatisEntity> writer(SqlSessionFactory sqlSessionFactory) {
        MyBatisBatchItemWriter<MemberCouponIssueMybatisEntity> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory); // DB 연결
        writer.setStatementId("com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberCouponIssueMapper.insertMemberCouponIssue"); // Mapper 쿼리 ID
        return writer;
    }
}
