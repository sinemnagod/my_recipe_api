package com.recipe_app.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.env.Environment;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    private final Environment env;

    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    @Value("${MYSQL_URL:}")
    private String mysqlUrl;

    @Value("${MYSQLUSER:}")
    private String mysqlUser;

    @Value("${MYSQLPASSWORD:}")
    private String mysqlPassword;

    @Bean
    public DataSource dataSource() {
        try {
            logger.info("Initializing database connection...");

            // Log all environment variables (excluding sensitive data)
            env.getActiveProfiles();
            logger.info("Active profiles: {}", String.join(", ", env.getActiveProfiles()));

            // Validate database configuration
            if (mysqlUrl == null || mysqlUrl.trim().isEmpty()) {
                logger.error("MYSQL_URL is not set!");
                throw new IllegalStateException("Database URL is not configured");
            }

            if (mysqlUser == null || mysqlUser.trim().isEmpty()) {
                logger.error("MYSQLUSER is not set!");
                throw new IllegalStateException("Database username is not configured");
            }

            if (mysqlPassword == null || mysqlPassword.trim().isEmpty()) {
                logger.error("MYSQLPASSWORD is not set!");
                throw new IllegalStateException("Database password is not configured");
            }

            String jdbcUrl = mysqlUrl.startsWith("jdbc:") ? mysqlUrl : "jdbc:" + mysqlUrl;
            logger.info("Database URL format: {}", jdbcUrl.replaceAll("://.*@", "://*****@"));

            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUsername(mysqlUser);
            dataSource.setPassword(mysqlPassword);
            dataSource.setMaximumPoolSize(5);
            dataSource.setMinimumIdle(2);
            dataSource.setConnectionTimeout(20000);
            dataSource.setIdleTimeout(300000);
            dataSource.setMaxLifetime(1200000);

            // Test the connection
            try {
                dataSource.getConnection().close();
                logger.info("Successfully established database connection");
            } catch (Exception e) {
                logger.error("Failed to establish database connection", e);
                throw new IllegalStateException("Could not establish database connection", e);
            }

            return dataSource;

        } catch (Exception e) {
            logger.error("Failed to configure database", e);
            throw new IllegalStateException("Database configuration failed", e);
        }
    }
}