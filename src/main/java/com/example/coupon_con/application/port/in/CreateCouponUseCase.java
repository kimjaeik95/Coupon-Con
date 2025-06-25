package com.example.coupon_con.application.port.in;

import com.example.coupon_con.application.port.in.dto.CreateCouponCommand;

/**
 * packageName    : com.example.coupon_con.application.port.in
 * fileName       : CreateCouponUseCase
 * author         : JAEIK
 * date           : 6/20/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 6/20/25       JAEIK       최초 생성
 */
public interface CreateCouponUseCase {
    /*
    이 프로젝트는 기능이 많이 나오지않아서 기능별로 UseCase 를 놔눌 생각이다.
    만약 기능이 더 많아진다면  CQRS 스타일로  Query = 조회, Command = 입력/수정/삭제
    CouponCommandUseCase (입력,수정,삭제)용  ,  CouponQueryUseCase (조회) 부분으로 놔 눌 수도 있다.
     */
    void createCoupon(CreateCouponCommand couponCommand);
}
