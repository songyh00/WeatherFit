package com.weatherfit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 프로젝트를 실행하는 메인 클래스
 * - Spring Boot 애플리케이션을 시작한다
 * - JPA 리포지토리가 있는 패키지를 지정한다
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.weatherfit.backend")
public class WeatherFitBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherFitBackendApplication.class, args);
    }
}
