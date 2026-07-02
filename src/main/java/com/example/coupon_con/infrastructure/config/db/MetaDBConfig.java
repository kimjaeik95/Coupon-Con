package com.example.coupon_con.infrastructure.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * packageName    : com.example.coupon_con.infrastructure.config.db
 * fileName       : MetaDBconfig
 * author         : JAEIK
 * date           : 9/6/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/6/25        JAEIK       최초 생성
 */
@Configuration
public class MetaDBConfig {
    @Bean
    public DataSource metaDBSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3307/meta_db?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager metaTransactionManager() {
        return new DataSourceTransactionManager(metaDBSource());
    }
}

