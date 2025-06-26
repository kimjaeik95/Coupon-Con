package com.example.coupon_con.application.service;

import com.example.coupon_con.application.mapper.CouponDtoMapper;
import com.example.coupon_con.application.port.in.*;
import com.example.coupon_con.application.port.in.dto.CouponResponse;
import com.example.coupon_con.application.port.in.dto.CreateCouponCommand;
import com.example.coupon_con.application.port.in.dto.DeleteCouponCommand;
import com.example.coupon_con.application.port.in.dto.UpdateCouponCommand;
import com.example.coupon_con.application.port.out.*;
import com.example.coupon_con.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
public class CouponService implements
        CreateCouponUseCase, GetAllCouponUseCase, DeleteCouponUseCase,
        UpdateCouponUseCase, FindCouponUseCase {
    private final CreateCouponPort createCouponPort;
    private final GetAllCouponPort getAllCouponPort;
    private final DeleteCouponByIdPort deleteCouponByIdPort;
    private final FindCouponPort findCouponPort;
    private final UpdateCouponPort updateCouponPort;
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
        findCouponPort.findById(command.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("삭제할 쿠폰이 없습니다."));
        deleteCouponByIdPort.deleteById(command.getCouponId());
    }

    @Override
    public Coupon updateCoupon(UpdateCouponCommand command) {
        Coupon coupon = findCouponPort.findById(command.getCouponId())
                        .orElseThrow(()-> new IllegalArgumentException("쿠폰을 찾을 수 없습니다."));

        coupon.updateCoupon(
                command.getCouponName(),
                command.getCouponNumber(),
                command.getQuantity(),
                command.getIsDeleted());

        return updateCouponPort.update(coupon);
    }

    @Override
    public Coupon findByCoupon(Long couponId) {
       return findCouponPort.findById(couponId)
                .orElseThrow(()-> new IllegalArgumentException("쿠폰을 찾을 수 없습니다."));

    }
}
