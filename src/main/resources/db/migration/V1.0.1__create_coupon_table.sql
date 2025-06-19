CREATE TABLE coupon (
coupon_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '쿠폰ID',
coupon_name VARCHAR(255) COMMENT '쿠폰명',
coupon_number VARCHAR(255) UNIQUE COMMENT '쿠폰번호',
coupon_created_at TIMESTAMP COMMENT '쿠폰생성일자',
coupon_expired_at TIMESTAMP COMMENT '쿠폰만료기간',
quantity INT COMMENT '쿠폰수량',
is_deleted BOOLEAN DEFAULT FALSE COMMENT 'soft delete 삭제 여부'
);

