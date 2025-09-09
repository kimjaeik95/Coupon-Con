package com.example.coupon_con.infrastructure.batch.reader;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public MyBatisPagingItemReader<MemberMybatisEntity> memberReader(SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader<MemberMybatisEntity> reader = new MyBatisPagingItemReader<>();
        reader.setQueryId("com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper.findAllMember");
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setPageSize(1000);
        return reader;
    }
}
