package com.targetcart.ai.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Base class for tests that need the MySQL development database.
 *
 * <p>Datasource values are resolved at runtime in this order:
 * <ol>
 *   <li>Environment variables DB_URL / DB_USERNAME / DB_PASSWORD (explicit override)</li>
 *   <li>The repo's db/.env file (MYSQL_DATABASE / MYSQL_USER / MYSQL_PASSWORD)</li>
 *   <li>Defaults defined in src/test/resources/application-test.properties</li>
 * </ol>
 *
 * <p>This keeps real credentials out of the repository while letting
 * {@code mvnw test} work out of the box once the MySQL dev container is up.
 */
@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractMySqlTest {

    @DynamicPropertySource
    static void mysqlDataSourceProperties(DynamicPropertyRegistry registry) {
        Map<String, String> dotEnv = loadDotEnv();
        registry.add("spring.datasource.url",
                () -> firstNonBlank(System.getenv("DB_URL"),
                        "jdbc:mysql://localhost:3306/" + dotEnv.getOrDefault("MYSQL_DATABASE", "targetcart_ai")));
        registry.add("spring.datasource.username",
                () -> firstNonBlank(System.getenv("DB_USERNAME"),
                        dotEnv.getOrDefault("MYSQL_USER", "targetcart")));
        registry.add("spring.datasource.password",
                () -> firstNonBlank(System.getenv("DB_PASSWORD"),
                        dotEnv.getOrDefault("MYSQL_PASSWORD", "")));
    }

    private static String firstNonBlank(String first, String fallback) {
        return (first == null || first.isBlank()) ? fallback : first;
    }

    private static Map<String, String> loadDotEnv() {
        Map<String, String> values = new HashMap<>();
        for (Path candidate : dotEnvCandidates()) {
            if (Files.isRegularFile(candidate)) {
                try {
                    for (String line : Files.readAllLines(candidate)) {
                        String trimmed = line.trim();
                        if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                            continue;
                        }
                        int separator = trimmed.indexOf('=');
                        if (separator > 0) {
                            values.put(trimmed.substring(0, separator).trim(),
                                    trimmed.substring(separator + 1).trim());
                        }
                    }
                } catch (IOException e) {
                    // ignore and fall back to defaults
                }
                break;
            }
        }
        return values;
    }

    private static Path[] dotEnvCandidates() {
        Path cwd = Path.of(System.getProperty("user.dir", "."));
        return new Path[]{
                cwd.resolve("../db/.env"),
                cwd.resolve("db/.env"),
                Path.of("..", "db", ".env"),
                Path.of("db", ".env")
        };
    }
}