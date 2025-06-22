package com.example.coupon_con.infrastructure.adapter.in.web;

import com.example.coupon_con.application.port.in.CreateCouponUseCase;
import com.example.coupon_con.application.port.in.dto.CreateCouponCommand;
import com.example.coupon_con.application.port.in.dto.CreateCouponRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.in.web
 * fileName       : CouponController
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CreateCouponUseCase createCouponUseCase;

    @PostMapping("/create")
    public ResponseEntity<?> saveCoupon(@RequestBody CreateCouponRequest couponRequest) {
        CreateCouponCommand couponCommand = couponRequest.toCreateCouponCommand();
        createCouponUseCase.createCoupon(couponCommand);
        return ResponseEntity.ok().build();
    }
}
