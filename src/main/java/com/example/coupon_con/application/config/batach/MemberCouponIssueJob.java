package com.example.coupon_con.application.config.batach;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberCouponIssueMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;
import com.example.coupon_con.infrastructure.batch.processor.MemberCouponIssueProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * packageName    : com.example.coupon_con.infrastructure.batch.reader
 * fileName       : MemberReader
 * author         : JAEIK
 * date           : 9/8/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/8/25        JAEIK       최초 생성
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class MemberCouponIssueJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final MyBatisPagingItemReader<MemberMybatisEntity> reader;
    private final MyBatisBatchItemWriter<MemberCouponIssueMybatisEntity> writer;
    private final MemberCouponIssueProcessor processor;


    // Job 정의
    @Bean
    public Job memberCouponIssueJob() {
        return new JobBuilder("memberCouponIssueJob", jobRepository) // job 이름설정
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("Job 시작: {} ", jobExecution.getJobInstance().getJobName());
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("Job 종료: {} , 상태: {} , 시작시간: {} , 종료시간: {}",
                                jobExecution.getJobInstance().getJobName(),
                                jobExecution.getStatus(),  // SUCCESS, FAILED 등
                                jobExecution.getStartTime(),
                                jobExecution.getEndTime()
                        );
                    }
                })
                .start(memberCouponIssueStep())
                .build();
    }

    @Bean
    public Step memberCouponIssueStep() {
        return new StepBuilder("memberCouponIssueStep", jobRepository)
                // chunk 기반 한 번에 처리할 데이터 수, 트랜잭션 관리 매니져 (commit/rollback) 설정
                .<MemberMybatisEntity, MemberCouponIssueMybatisEntity> chunk(1000, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
