package com.example.coupon_con.infrastructure.batch.writer;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberCouponIssueMybatisEntity;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

/**
 * packageName    : com.example.coupon_con.infrastructure.batch.writer
 * fileName       : BatchInsertWriter
 * author         : JAEIK
 * date           : 9/19/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/19/25        JAEIK       최초 생성
 */
@RequiredArgsConstructor
public class BatchInsertWriter implements ItemWriter<MemberCouponIssueMybatisEntity> {
    private final SqlSession session;

    @Override
    public void write(Chunk<? extends MemberCouponIssueMybatisEntity> chunk) throws Exception {
        session.insert(
                "com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberCouponIssueMapper.batchInsertMemberCouponIssue",
                chunk.getItems() // Chunk 내부에서 List 추출
        );
    }
}
