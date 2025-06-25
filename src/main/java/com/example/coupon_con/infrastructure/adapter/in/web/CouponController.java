package com.example.coupon_con.infrastructure.adapter.in.web;

import com.example.coupon_con.application.port.in.CreateCouponUseCase;
import com.example.coupon_con.application.port.in.DeleteCouponUseCase;
import com.example.coupon_con.application.port.in.GetAllCouponUseCase;
import com.example.coupon_con.application.port.in.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/api")
@RequiredArgsConstructor
public class CouponController {
    private final CreateCouponUseCase createCouponUseCase;
    private final GetAllCouponUseCase getAllCouponUseCase;
    private final DeleteCouponUseCase deleteCouponUseCase;

    @PostMapping("/coupon/create")
    public ResponseEntity<?> saveCoupon(@RequestBody CreateCouponRequest couponRequest) {
        CreateCouponCommand couponCommand = couponRequest.toCreateCouponCommand();
        createCouponUseCase.createCoupon(couponCommand);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
        List<CouponResponse> couponsResponse = getAllCouponUseCase.getAllCoupon();
        return ResponseEntity.ok().body(couponsResponse);

    }

    @DeleteMapping("/coupon/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable("couponId") Long couponId) {
        // 필드로 받을게 couponId 니까 간단하게 생성자로 호출로 변환 /비교  MVC 패턴에서는 변경필요없음
        DeleteCouponCommand deleteCouponCommand = new DeleteCouponCommand(couponId);
        deleteCouponUseCase.deleteByIdCoupon(deleteCouponCommand);
        return ResponseEntity.noContent().build();
    }
}
