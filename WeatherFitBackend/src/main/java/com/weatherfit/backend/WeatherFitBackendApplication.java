package com.weatherfit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WeatherFit 백엔드 애플리케이션의 메인 클래스
 *
 * - Spring Boot 애플리케이션을 시작한다.
 * - @SpringBootApplication은 자동으로:
 *    1. 컴포넌트 스캔(ComponentScan)
 *    2. 자동 설정(EnableAutoConfiguration)
 *    3. 설정 파일 등록(Configuration)을 포함한다.
 * - JPA 리포지토리는 이 패키지 기준으로 자동 스캔된다.
 */
@SpringBootApplication
public class WeatherFitBackendApplication {

    public static void main(String[] args) {
        // Spring Boot 애플리케이션 실행
        SpringApplication.run(WeatherFitBackendApplication.class, args);
    }

}