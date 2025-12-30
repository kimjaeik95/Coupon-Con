package com.example.coupon_con.infrastructure.batch.reader;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
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
@StepScope
@RequiredArgsConstructor
public class MemberReader implements ItemStreamReader<MemberMybatisEntity> {
    private final MemberMapper memberMapper;

    @Value("#{jobParameters['couponId']}")
    private long couponId;

    private static final String LAST_ID_KEY = "lastId";

    private long lastId = 0L;
    private int index = 0;
    private final int pageSize = 2000;

    private List<MemberMybatisEntity> buffer = new ArrayList<>();

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED) // 트랜잭션 없이
    public MemberMybatisEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (index >= buffer.size()) {
            buffer = memberMapper.findAllPaged(lastId, pageSize, couponId);
            index = 0;
            if (buffer.isEmpty()) {
                return null;
            }
            lastId = buffer.get(buffer.size() - 1).getMemberId();
        }
        return buffer.get(index++);
    }

    // restart 마지막 id 저장
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey(LAST_ID_KEY)) {
            this.lastId = executionContext.getLong(LAST_ID_KEY);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putLong(LAST_ID_KEY, lastId);
    }

    @Override
    public void close() throws ItemStreamException {
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

