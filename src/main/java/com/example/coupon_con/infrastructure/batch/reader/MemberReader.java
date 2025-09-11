package com.example.coupon_con.infrastructure.batch.reader;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * packageName    : com.example.coupon_con.infrastructure.batach
 * fileName       : CouponJob
 * author         : JAEIK
 * date           : 9/8/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/8/25        JAEIK       최초 생성
 */
@Configuration
public class MemberReader {

    @Bean
    @StepScope
    public MyBatisCursorItemReader<MemberMybatisEntity> reader(SqlSessionFactory sqlSessionFactory) {
        MyBatisCursorItemReader<MemberMybatisEntity> reader = new MyBatisCursorItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper.findAll");
        return reader;
    }
//    public MyBatisPagingItemReader<MemberMybatisEntity> reader(SqlSessionFactory sqlSessionFactory) {
//        MyBatisPagingItemReader<MemberMybatisEntity> reader = new MyBatisPagingItemReader<>();
//        reader.setQueryId("com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper.findAll");
//        reader.setSqlSessionFactory(sqlSessionFactory);
//        reader.setPageSize(1000);
//        reader.setParameterValues(Map.of("lastId", 0L, "pageSize", 1000));
//        reader.setSaveState(true); // job 재실행시 마지막 read 위치 기억
//        return reader;
    }

