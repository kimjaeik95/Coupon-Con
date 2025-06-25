package com.example.coupon_con.application.service;

import com.example.coupon_con.application.mapper.CouponDtoMapper;
import com.example.coupon_con.application.port.in.CreateCouponUseCase;
import com.example.coupon_con.application.port.in.DeleteCouponUseCase;
import com.example.coupon_con.application.port.in.GetAllCouponUseCase;
import com.example.coupon_con.application.port.in.dto.CouponResponse;
import com.example.coupon_con.application.port.in.dto.CreateCouponCommand;
import com.example.coupon_con.application.port.in.dto.DeleteCouponCommand;
import com.example.coupon_con.application.port.out.CreateCouponPort;
import com.example.coupon_con.application.port.out.DeleteCouponByIdPort;
import com.example.coupon_con.application.port.out.GetAllCouponPort;
import com.example.coupon_con.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.example.coupon_con.application.service
 * fileName       : CouponService
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
@RequiredArgsConstructor
@Service
public class CouponService implements CreateCouponUseCase, GetAllCouponUseCase, DeleteCouponUseCase {
    private final CreateCouponPort createCouponPort;
    private final GetAllCouponPort getAllCouponPort;
    private final DeleteCouponByIdPort deleteCouponByIdPort;
    private final CouponDtoMapper couponDtoMapper;

    @Override
    public void createCoupon(CreateCouponCommand couponCommand) {
        createCouponPort.save(couponDtoMapper.toCouponDomain(couponCommand));
    }

    @Override
    public List<CouponResponse> getAllCoupon() {
        // Domain → Response DTO 변환만 수행
        List<Coupon> coupons = getAllCouponPort.findAll();
        return coupons.stream()
                .map(couponDtoMapper::toCouponResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByIdCoupon(DeleteCouponCommand command) {
        deleteCouponByIdPort.deleteById(command.getCouponId());
    }
}
