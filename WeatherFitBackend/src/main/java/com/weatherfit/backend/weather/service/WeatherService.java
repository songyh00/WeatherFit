package com.weatherfit.backend.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherfit.backend.weather.dto.DailyTemperatureDto;
import com.weatherfit.backend.weather.dto.HourlyTemperatureDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ✅ 하루 최소/최대 온도 조회
    public DailyTemperatureDto getDailyTemperature(String baseDate, String baseTime, String nx, String ny) {
        String url = buildUrl(baseDate, baseTime, nx, ny);
        ResponseEntity<String> response = fetchWeatherData(url);

        int minTemp = Integer.MAX_VALUE;
        int maxTemp = Integer.MIN_VALUE;

        if (response != null && response.getBody() != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode items = objectMapper.readTree(response.getBody())
                        .path("response").path("body").path("items").path("item");

                for (JsonNode item : items) {
                    if ("TMP".equals(item.path("category").asText())) { // TMP = 예보 기온
                        int temp = Integer.parseInt(item.path("fcstValue").asText());
                        minTemp = Math.min(minTemp, temp);
                        maxTemp = Math.max(maxTemp, temp);
                    }
                }
                return new DailyTemperatureDto(minTemp, maxTemp);
            } catch (Exception e) {
                System.err.println("❗ getDailyTemperature 파싱 오류: " + e.getMessage());
            }
        }
        return new DailyTemperatureDto(0, 0); // 실패 시 기본값
    }

    // ✅ 3시간 간격 시간대별 온도 조회
    public List<HourlyTemperatureDto> getHourlyTemperature(String baseDate, String baseTime, String nx, String ny) {
        String url = buildUrl(baseDate, baseTime, nx, ny);
        ResponseEntity<String> response = fetchWeatherData(url);

        List<HourlyTemperatureDto> hourlyList = new ArrayList<>();

        if (response != null && response.getBody() != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode items = objectMapper.readTree(response.getBody())
                        .path("response").path("body").path("items").path("item");

                for (JsonNode item : items) {
                    if ("TMP".equals(item.path("category").asText())) { // TMP = 3시간 간격 기온
                        String fcstTime = item.path("fcstTime").asText();
                        int temp = Integer.parseInt(item.path("fcstValue").asText());
                        hourlyList.add(new HourlyTemperatureDto(fcstTime, temp));
                    }
                }
                return hourlyList;
            } catch (Exception e) {
                System.err.println("❗ getHourlyTemperature 파싱 오류: " + e.getMessage());
            }
        }
        return new ArrayList<>(); // 실패 시 빈 리스트
    }

    // ✅ 공통 - 요청 URL 조립
    private String buildUrl(String baseDate, String baseTime, String nx, String ny) {
        return apiUrl +
                "/getVilageFcst?serviceKey=" + apiKey +
                "&pageNo=1&numOfRows=1000&dataType=JSON" +
                "&base_date=" + baseDate +
                "&base_time=" + baseTime +
                "&nx=" + nx +
                "&ny=" + ny;
    }

    // ✅ 공통 - API 호출
    private ResponseEntity<String> fetchWeatherData(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            System.err.println("❗ fetchWeatherData 호출 오류: " + e.getMessage());
            return null;
        }
    }
}
