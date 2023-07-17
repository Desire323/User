package com.desire323.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(System.getenv("SPRING_DATASOURCE_DRIVER-CLASS-NAME"));
        dataSource.setUrl(System.getenv("SPRING_DATASOURCE_URL"));
        dataSource.setUsername(System.getenv("SPRING_DATASOURCE_USERNAME"));
        dataSource.setPassword(System.getenv("SPRING_DATASOURCE_PASSWORD"));
        return dataSource;
    }
//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3305/user");
//        dataSource.setUsername("user");
//        dataSource.setPassword("password");
//        return dataSource;
//    }
}

