package com.example.coupon_con.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Service;

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
public class BatchCouponIssueRecoveryService {
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    public void restartFailedJob(Long jobExecutionId) throws Exception {
        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);

        if (jobExecution == null) {
            throw new IllegalArgumentException("존재 하지 않는 jobExecutionId 입니다");
        }

        BatchStatus status = jobExecution.getStatus();

        if (status != BatchStatus.FAILED && status != BatchStatus.STOPPED) {
            throw new IllegalArgumentException("재실행 불가 상태입니다. 현재 상태 확인 해주세요: " + status);
        }

        jobOperator.restart(jobExecutionId);
    }
}
