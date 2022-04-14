package io.surati.gap.maccount.module.domain.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
final class PgMaDemoDatabaseITCase {

    @Container
    private static final GenericContainer<?> container =
        new PostgreSQLContainer<>("postgres:11.0")
            .withDatabaseName("db_gap")
            .withUsername("gap")
            .withPassword("admin");

    @Test
    void migrates() {
        final HikariConfig config = new HikariConfig();
        final JdbcDatabaseContainer<?> jdbcc = (JdbcDatabaseContainer<?>) PgMaDemoDatabaseITCase.container;
        config.setJdbcUrl(jdbcc.getJdbcUrl());
        config.setUsername(jdbcc.getUsername());
        config.setPassword(jdbcc.getPassword());
        config.setDriverClassName(jdbcc.getDriverClassName());
        new MaDemoDatabase(
            new HikariDataSource(config)
        );
    }
}
