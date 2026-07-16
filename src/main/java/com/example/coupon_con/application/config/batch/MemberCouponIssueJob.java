package com.example.coupon_con.application.config.batch;

import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberCouponIssueMybatisEntity;
import com.example.coupon_con.infrastructure.adapter.out.persistence.entity.MemberMybatisEntity;

import com.example.coupon_con.infrastructure.batch.processor.MemberCouponIssueProcessor;
import com.example.coupon_con.infrastructure.batch.writer.BatchInsertWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
public class MemberCouponIssueJob {
    private static final int GRID_SIZE = 4;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager metaTransactionManager;
    private final ItemReader<MemberMybatisEntity> reader;
    private final BatchInsertWriter writer;
    private final MemberCouponIssueProcessor processor;
    private final MemberRangePartitioner partitioner;

    public MemberCouponIssueJob(JobRepository jobRepository,
                                @Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager,
                                @Qualifier("memberReader") ItemReader<MemberMybatisEntity> reader, BatchInsertWriter writer,
                                MemberCouponIssueProcessor processor,
                                MemberRangePartitioner partitioner) {
        this.jobRepository = jobRepository;
        this.metaTransactionManager = metaTransactionManager;
        this.reader = reader;
        this.writer = writer;
        this.processor = processor;
        this.partitioner = partitioner;
    }

    // Job 정의
    @Bean(name = "couponIssueJob")
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
                .start(memberCouponIssueStepManger())
                .build();
    }

    // 파티션 나누고 Worker 스탭에 분배하는 매니저 (Partitioner -> 파티션생성 -> PartitionHandler 전달 -> Worker step 실행)
    @Bean
    public Step memberCouponIssueStepManger() {
        return new StepBuilder("memberCouponIssueStep.manger", jobRepository)
                .partitioner("memberCouponIssueStep", partitioner)
                .step(memberCouponIssueStep())
                .partitionHandler(partitionHandler())
                .build();

    }

    // Worker step  몇개 실행할지
    @Bean
    public TaskExecutorPartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setStep(memberCouponIssueStep());
        partitionHandler.setTaskExecutor(taskExecutor());
        partitionHandler.setGridSize(GRID_SIZE);
        return partitionHandler;
    }

    // Worker Step 실제 하는 일을 하는 Step  (단일스레드 or 멀티스레드)
    @Bean
    public Step memberCouponIssueStep() {
        return new StepBuilder("memberCouponIssueStep", jobRepository)
                // chunk 기반 한 번에 처리할 데이터 수, 트랜잭션 관리 매니져 (commit/rollback) 설정
                .<MemberMybatisEntity, MemberCouponIssueMybatisEntity> chunk(2000, metaTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // 멀티스레드 생성
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(GRID_SIZE);
        executor.setMaxPoolSize(GRID_SIZE);
        executor.setThreadNamePrefix("coupon-issue-");
        executor.initialize();
        return executor;
    }
}
