package com.weatherfit.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
        // UriComponentsBuilder를 사용하여 URL을 안전하게 생성
        String url = UriComponentsBuilder.fromHttpUrl(apiurl)
                .pathSegment("getUltraSrtNcst")
                .queryParam("serviceKey", apikey)
                .queryParam("&pageNo", 1)
                .queryParam("&numOfRows", 1000)
                .queryParam("&dataType", "JSON")
                .queryParam("&base_date", baseDate)
                .queryParam("&base_time", baseTime)
                .queryParam("&nx", nx)
                .queryParam("&ny", ny)
                .build()
                .toUriString();                     // URL을 문자열로 변환

        System.out.println("Generated URL: " + url);
        // API 호출
        return restTemplate.getForObject(url, String.class);

    }


}
