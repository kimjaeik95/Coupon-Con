package com.example.coupon_con.infrastructure.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.example.coupon_con.infrastructure.adapter.in.web
 * fileName       : CouponPageController
 * author         : JAEIK
 * date           : 7/6/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/6/26        JAEIK       최초 생성
 */
@Controller
public class CouponPageController {
    @GetMapping("/api/coupon")
    public String couponPage() {
        return "coupon-issue"; // templates/coupon-issue.html
    }
}
