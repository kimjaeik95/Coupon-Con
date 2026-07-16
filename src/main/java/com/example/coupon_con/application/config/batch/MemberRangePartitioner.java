package com.example.coupon_con.application.config.batch;

import com.example.coupon_con.infrastructure.adapter.out.persistence.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.example.coupon_con.application.config.batch
 * fileName       : MemberRangePartitioner
 * author         : JAEIK
 * date           : 7/15/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/15/26        JAEIK       최초 생성
 */
@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class MemberRangePartitioner implements Partitioner {
    private final MemberMapper memberMapper;

    @Value("#{jobParameters['couponId']}")
    private long couponId;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        log.info("Partitioner 호출됨, gridSize={}", gridSize);
        Long minId = memberMapper.findMinId();
        Long maxId = memberMapper.findMaxId();

        // 여러 개의 파티션(구간)을 관리하는 Map
        Map<String, ExecutionContext> result = new HashMap<>();

        if (minId == null || maxId == null) {
            ExecutionContext ctx = new ExecutionContext();
            ctx.putLong("minId", 0L);
            ctx.putLong("maxId", 0L);
            result.put("partition0", ctx);
            return result;
        }

        long targetSize = (maxId - minId) / gridSize + 1; // 한 파티션당 몇명 처리할지  +1 은 나머지 처리용으로 필수
        long start = minId;
        long end = start + targetSize - 1;
        int partitionNumber = 0; // 파티션 번호

        while (start <= maxId) {
            ExecutionContext ctx = new ExecutionContext();
            ctx.putLong("minId", start);
            ctx.putLong("maxId", Math.min(end, maxId)); // 파티션범위보다 실제 데이터는 작을 수 있음 불필요한 DB 스캔 방지용
            result.put("partition" + partitionNumber, ctx);

            // 다음 파티션 넘어가기위한 위치 계산
            start += targetSize;
            end += targetSize;
            partitionNumber++;
        }
        log.info("파티션 생성: {}", result.keySet());
        return result;
    }
}
