package com.weatherfit.backend.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public String getWeather(String baseDate, String baseTime, String nx, String ny) {
        // ✅ 동네예보(getVilageFcst)로 변경
        StringBuilder urlBuilder = new StringBuilder(apiurl)
                .append("/getVilageFcst")
                .append("?serviceKey=").append(apikey)
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
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.6312.86 Safari/537.36");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class
        );

        System.out.println("응답 상태 코드: " + response.getStatusCode());
        System.out.println("응답 본문: " + response.getBody());

        String responseBody = response.getBody();

        if (responseBody != null && responseBody.trim().startsWith("{")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode items = root.path("response").path("body").path("items").path("item");

                StringBuilder result = new StringBuilder();

                for (JsonNode item : items) {
                    String category = item.path("category").asText();
                    String value = item.path("fcstValue").asText(); // ✅ 동네예보는 obsrValue가 아니라 fcstValue
                    result.append(category).append(": ").append(value).append("\n");
                }

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "JSON 파싱 실패";
            }
        } else {
            return "서버에서 JSON이 아닌 응답을 받았습니다:\n\n" + responseBody;
        }
    }
}