package com.example.coupon_con.infrastructure.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * packageName    : com.example.coupon_con.infrastructure.config.db
 * fileName       : MainDBConfig
 * author         : JAEIK
 * date           : 9/6/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/6/25        JAEIK       최초 생성
 */
@Configuration
@MapperScan(basePackages = "com.example.coupon_con.infrastructure.adapter.out.persistence.mapper", sqlSessionFactoryRef = "mainSqlSessionFactory")
public class MainDBConfig {

    @Primary
    @Bean(name = "mainDBSource")
    public DataSource mainDBSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3307/coupon-con?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true");
        ds.setUsername("root");
        ds.setPassword("1234");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setMaximumPoolSize(100);
        return ds;
    }


    @Primary
    @Bean
    public PlatformTransactionManager mainTransactionManager(@Qualifier("mainDBSource") DataSource mainDBSource) {
        return new DataSourceTransactionManager(mainDBSource);
    }

    // mybatis 설정
    @Bean
    public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDBSource")DataSource mainDBSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(mainDBSource);

        // 🔹 MyBatis Configuration 직접 설정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);  // snake_case → camelCase 자동 매핑
        factoryBean.setConfiguration(configuration);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml")
        );

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate mainsqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // primary meta 로인식해서 flyway 스크립트 mainDB 로지정
    @Bean
    public Flyway flyway(@Qualifier("mainDBSource") DataSource mainDataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(mainDataSource)
                .locations("classpath:db/migration/main")
                .load();
        flyway.migrate(); // 명시적 실행
        return flyway;
    }
}


