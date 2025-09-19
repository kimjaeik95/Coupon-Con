package com.example.coupon_con.infrastructure.batch.reader;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@Component
@RequiredArgsConstructor
public class MemberReader implements ItemReader<MemberMybatisEntity> {
    private final MemberMapper memberMapper;

    private long lastId = 0L;
    private final int pageSize = 2000;

    private List<MemberMybatisEntity> buffer = new ArrayList<>();
    private int index = 0;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED) // 트랜잭션 없이
    public MemberMybatisEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (index >= buffer.size()) {
            buffer = memberMapper.findAllPaged(lastId, pageSize);
            index = 0;
            if (buffer.isEmpty()) {
                return null;
            }
            lastId = buffer.get(buffer.size() - 1).getMemberId();
        }
        return buffer.get(index++);
    }


//    @Bean
//    @StepScope
//    public MyBatisCursorItemReader<MemberMybatisEntity> reader(SqlSessionFactory sqlSessionFactory) {
//        MyBatisCursorItemReader<MemberMybatisEntity> reader = new MyBatisCursorItemReader<>();
//        reader.setSqlSessionFactory(sqlSessionFactory);
//        reader.setQueryId("com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper.findAll");
//        return reader;
//    }
//    public MyBatisPagingItemReader<MemberMybatisEntity> reader(SqlSessionFactory sqlSessionFactory) {
//        MyBatisPagingItemReader<MemberMybatisEntity> reader = new MyBatisPagingItemReader<>();
//        reader.setQueryId("com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper.findAll");
//        reader.setSqlSessionFactory(sqlSessionFactory);
//        reader.setPageSize(1000);
//        reader.setParameterValues(Map.of("lastId", 0L, "pageSize", 1000));
//        reader.setSaveState(true); // job 재실행시 마지막 read 위치 기억
//        return reader;
    }

