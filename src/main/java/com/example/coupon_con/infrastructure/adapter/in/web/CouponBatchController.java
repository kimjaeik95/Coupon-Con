package com.example.coupon_con.infrastructure.adapter.in.web;

import com.example.coupon_con.application.service.CouponService;
import com.example.coupon_con.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.in.web
 * fileName       : CouponBatchController
 * author         : JAEIK
 * date           : 9/9/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/9/25        JAEIK       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class CouponBatchController {
    private final JobLauncher jobLauncher;
    private final Job memberCouponIssueJob;
    private final CouponService couponService;

    @PostMapping("/batch/coupon")
    public ResponseEntity<String> MemberCouponBatch(@RequestParam("couponName") String couponName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Coupon couponId = couponService.findByCouponName(couponName);

        JobParameters parameters = new JobParametersBuilder()
                .addLong("couponId", couponId.getCouponId())
                .toJobParameters();

        jobLauncher.run(memberCouponIssueJob, parameters);
        return ResponseEntity.ok().build();
    }
}
