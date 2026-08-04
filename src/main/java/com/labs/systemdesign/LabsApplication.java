package com.labs.systemdesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

/**
 * System Design Labs for Spring Boot.
 *
 * Each package under this one is a self-contained exercise with:
 *   - starter code containing // TODO markers
 *   - a failing test in the mirror package under src/test
 *
 * Goal: run `mvn test`, watch the reds, and code until green.
 *
 * Caching (Ex03) and Retry (Ex11) features are enabled here globally.
 */
@SpringBootApplication
@EnableCaching
@EnableRetry
public class LabsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LabsApplication.class, args);
    }
}
