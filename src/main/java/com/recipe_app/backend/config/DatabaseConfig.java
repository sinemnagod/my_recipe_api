package com.recipe_app.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${MYSQL_URL:}")
    private String mysqlUrl;

    @Value("${MYSQLUSER:}")
    private String mysqlUser;

    @Value("${MYSQLPASSWORD:}")
    private String mysqlPassword;

    @Bean
    public DataSource dataSource() {
        String jdbcUrl = mysqlUrl.startsWith("jdbc:") ? mysqlUrl : "jdbc:" + mysqlUrl;

        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(mysqlUser)
                .password(mysqlPassword)
                .build();
    }
}