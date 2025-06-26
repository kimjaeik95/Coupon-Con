CREATE TABLE member_coupon_issue (
coupon_issue_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '쿠폰발급 ID',
coupon_id BIGINT COMMENT '쿠폰 ID',
member_id BIGINT COMMENT '멤버 ID',
issued_at TIMESTAMP COMMENT '쿠폰발급시간',
used BOOLEAN DEFAULT FALSE COMMENT '사용유무',
used_at TIMESTAMP COMMENT '사용시간',
CONSTRAINT fk_coupon FOREIGN KEY (coupon_id) REFERENCES coupon(coupon_id),
CONSTRAINT uq_member_coupon UNIQUE(member_id, coupon_id),
CONSTRAINT fk_member FOREIGN key (member_id) REFERENCES member(member_id)
);

