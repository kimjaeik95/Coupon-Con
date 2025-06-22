package com.example.coupon_con;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.coupon_con.infrastructure.adapter.out.persistence.repository")
public class CouponConApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponConApplication.class, args);
	}

}
