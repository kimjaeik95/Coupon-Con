package com.example.coupon_con.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.coupon_con.application.service
 * fileName       : BatchCouponIssueRecoveryService
 * author         : JAEIK
 * date           : 12/30/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 12/30/25        JAEIK       최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BatchCouponIssueRecoveryService {
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;
    private final JdbcTemplate metaJdbcTemplate; // meta DB용

    public void restartFailedJob(Long jobExecutionId) throws Exception {
        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);
        if (jobExecution == null) {
            throw new IllegalArgumentException("존재 하지 않는 jobExecutionId 입니다");
        }

        BatchStatus status = jobExecution.getStatus();
        if (status != BatchStatus.FAILED && status != BatchStatus.STOPPED) {
            throw new IllegalArgumentException("재실행 불가 상태입니다. 현재 상태 확인 해주세요: " + status);
        }

        // 같은 JobInstance 내 좀비(END_TIME IS NULL) execution 사전 체크
        Long jobInstanceId = jobExecution.getJobInstance().getInstanceId();

        List<Long> zombieIds = metaJdbcTemplate.queryForList(
                "SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION " +
                        "WHERE JOB_INSTANCE_ID = ? AND END_TIME IS NULL",
                Long.class, jobInstanceId);

        if (!zombieIds.isEmpty()) {
            throw new IllegalStateException(
                    "재실행 불가: 비정상 종료된 것으로 의심되는 execution 존재 (수동 확인 필요) - "
                            + zombieIds);
            // 여기서 자동으로 강제 FAILED 처리하지 않는 걸 권장.
            // 진짜 다른 인스턴스에서 아직 돌고 있는 정상 케이스일 수도 있기 때문.
        }

        jobOperator.restart(jobExecutionId);
    }
}