package com.social.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

@Configuration
@Profile("local")
@Slf4j
public class LocalDbInitializer {

    private final DataSource dataSource;

    public LocalDbInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            var is = getClass().getClassLoader().getResourceAsStream("schema-h2.sql");
            if (is == null) {
                log.error("schema-h2.sql not found in classpath");
                return;
            }
            String sql = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .filter(line -> !line.trim().startsWith("--") && !line.trim().isEmpty())
                    .collect(java.util.stream.Collectors.joining(" "));
            stmt.execute(sql);
            log.info("H2 schema initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize H2 schema", e);
        }
    }
}
