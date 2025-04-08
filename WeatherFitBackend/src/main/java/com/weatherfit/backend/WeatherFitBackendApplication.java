package com.weatherfit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.weatherfit.backend.repository")
public class WeatherFitBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherFitBackendApplication.class, args);
    }
}
