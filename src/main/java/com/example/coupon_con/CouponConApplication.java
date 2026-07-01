package com.example.coupon_con;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CouponConApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponConApplication.class, args);
	}

}
