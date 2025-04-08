package com.weatherfit.backend.apiservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.weatherfit.backend.dto.TemperatureDto;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;


@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;

    @Value("${weather.api.url}")
    private String apiurl;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TemperatureDto getTemperature(String baseDate, String baseTime, String nx, String ny) {
        StringBuilder urlBuilder = new StringBuilder(apiurl)
                .append("/getUltraSrtNcst")
                .append("?serviceKey=").append(apikey.trim())
                .append("&pageNo=1")
                .append("&numOfRows=1000")
                .append("&dataType=JSON")
                .append("&base_date=").append(baseDate)
                .append("&base_time=").append(baseTime)
                .append("&nx=").append(nx)
                .append("&ny=").append(ny)
                .append("&_ts=").append(System.currentTimeMillis());


        String url = urlBuilder.toString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String responseBody = response.getBody();

        System.out.println("호출 URL: " + url);

        System.out.println("응답 본문: " + responseBody); // 확인용 로그


        if (responseBody != null && responseBody.trim().startsWith("{")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode items = root.path("response").path("body").path("items").path("item");

                for (JsonNode item : items) {
                    if ("T1H".equals(item.path("category").asText())) {
                        String temp = item.path("obsrValue").asText();

                        // JSON 파일로 저장
                        Map<String, String> weatherMap = new HashMap<>();
                        weatherMap.put("temperature", temp + " °C");
                        weatherMap.put("time", baseTime);
                        weatherMap.put("date", baseDate);

                        try (FileWriter file = new FileWriter("weather.json")) {
                            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, weatherMap);
                            System.out.println("✅ weather.json 파일로 저장 완료!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return new TemperatureDto(temp + " °C", baseTime, baseDate);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new TemperatureDto("정보 없음", baseTime, baseDate);
    }
}
