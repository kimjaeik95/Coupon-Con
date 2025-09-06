DELIMITER //

CREATE PROCEDURE InsertTestMembers(IN startIdx INT, IN count INT)
BEGIN
    DECLARE i INT DEFAULT 0;

    WHILE i <= count DO
        INSERT INTO MEMBER (name) VALUES (CONCAT('테스트멤버', startIdx + i));
        SET i = i + 1;
    END WHILE;
END //
DELIMITER ;

-- mysql ; 가 기본 구문 종료 문자이다.
-- MySQL 엔진 자체는 여전히 ;를 SQL 구문 종료로 이해한다. 그래서 프로시저 종료 구분을 // 로 바꿈
-- 내부 ;는 각각 SQL 구문 종료 // 는 프로시저 정의 종료
