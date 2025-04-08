package com.weatherfit.backend.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherfit.backend.weather.dto.TemperatureDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public TemperatureDto getTemperature(String baseDate, String baseTime, String nx, String ny) {
        StringBuilder urlBuilder = new StringBuilder(apiUrl)
                .append("/getVilageFcst")
                .append("?serviceKey=").append(apiKey)
                .append("&pageNo=1")
                .append("&numOfRows=1000")
                .append("&dataType=JSON")
                .append("&base_date=").append(baseDate)
                .append("&base_time=").append(baseTime)
                .append("&nx=").append(nx)
                .append("&ny=").append(ny);

        String url = urlBuilder.toString();
        System.out.println("Generated URL: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class
        );

        String responseBody = response.getBody();
        int minTemp = Integer.MAX_VALUE;
        int maxTemp = Integer.MIN_VALUE;

        if (responseBody != null && responseBody.trim().startsWith("{")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode items = root.path("response").path("body").path("items").path("item");

                for (JsonNode item : items) {
                    String category = item.path("category").asText();
                    if ("TMP".equals(category)) { // TMP = 기온
                        int temp = Integer.parseInt(item.path("fcstValue").asText());
                        minTemp = Math.min(minTemp, temp);
                        maxTemp = Math.max(maxTemp, temp);
                    }
                }

                return new TemperatureDto(minTemp, maxTemp);

            } catch (Exception e) {
                e.printStackTrace();
                return new TemperatureDto(0, 0);
            }
        } else {
            return new TemperatureDto(0, 0);
        }
    }
}
