package com.weatherfit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WeatherFit 백엔드 애플리케이션의 메인 클래스
 *
 * - Spring Boot 애플리케이션을 시작한다.
 * - JPA 리포지토리는 @SpringBootApplication 패키지 기준으로 자동 스캔된다.
 */
@SpringBootApplication
public class WeatherFitBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherFitBackendApplication.class, args);
    }
}
