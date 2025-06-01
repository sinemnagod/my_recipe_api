package com.recipe_app.backend.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        logger.info("Initializing DataSource with URL: {}", dataSourceUrl);
        logger.info("Using username: {}", dataSourceUsername);

        HikariDataSource dataSource = new HikariDataSource();
        try {
            dataSource.setJdbcUrl(dataSourceUrl);
            dataSource.setUsername(dataSourceUsername);
            dataSource.setPassword(dataSourcePassword);
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // Connection pool settings
            dataSource.setMaximumPoolSize(5);
            dataSource.setMinimumIdle(2);
            dataSource.setIdleTimeout(300000);
            dataSource.setConnectionTimeout(20000);
            dataSource.setMaxLifetime(1200000);
            dataSource.setAutoCommit(true);
            dataSource.setConnectionTestQuery("SELECT 1");

            // Test the connection
            logger.info("Testing database connection...");
            try (Connection conn = dataSource.getConnection()) {
                logger.info("Database connection test successful");
                logger.info("Connected to database: {}", conn.getCatalog());
            }

            return dataSource;
        } catch (Exception e) {
            logger.error("Failed to initialize datasource: {}", e.getMessage(), e);
            throw new RuntimeException("Could not initialize DataSource: " + e.getMessage(), e);
        }
    }
}