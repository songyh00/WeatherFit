package com.weatherfit.backend.weather.service;

import com.weatherfit.backend.weather.dto.ForecastDto;
import com.weatherfit.backend.weather.dto.HourlyTemperatureDto;
import com.weatherfit.backend.weather.dto.WeatherApiResponseDto;
import com.weatherfit.backend.weather.util.BaseDateTimeCalculator;
import com.weatherfit.backend.weather.util.LocationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WeatherService {

    private final WebClient webClient;
    private final String serviceKey;

    public WeatherService(WebClient.Builder webClientBuilder,
                          @Value("${weather.api.base-url}") String baseUrl,
                          @Value("${weather.api.service-key}") String serviceKey) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.serviceKey = serviceKey;
    }

    public ForecastDto getForecast(int nx, int ny, boolean tomorrow) {
        BaseDateTimeCalculator.DateTimeInfo dateTimeInfo = BaseDateTimeCalculator.getForecastDateTime(tomorrow);
        WeatherApiResponseDto responseDto = fetchWeatherApi(nx, ny, dateTimeInfo);
        return parseWeatherData(responseDto, dateTimeInfo.getTargetDate(), tomorrow);
    }

    private WeatherApiResponseDto fetchWeatherApi(int nx, int ny, BaseDateTimeCalculator.DateTimeInfo dateTimeInfo) {
        String uri = "/getVilageFcst?" +
                "serviceKey=" + serviceKey +
                "&pageNo=1" +
                "&numOfRows=1000" +
                "&dataType=JSON" +
                "&base_date=" + dateTimeInfo.getBaseDate() +
                "&base_time=" + dateTimeInfo.getBaseTime() +
                "&nx=" + nx +
                "&ny=" + ny;

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(WeatherApiResponseDto.class)
                .block();
    }

    private ForecastDto parseWeatherData(WeatherApiResponseDto responseDto, String targetDate, boolean tomorrow) {
        List<WeatherApiResponseDto.Item> items = responseDto.getResponse()
                .getBody()
                .getItems()
                .getItem();

        List<HourlyTemperatureDto> hourlyTemperatures = new ArrayList<>();
        List<Integer> tempsForAverage = new ArrayList<>();

        int maxTemp = Integer.MIN_VALUE;
        int minTemp = Integer.MAX_VALUE;
        String weatherType = "맑음";
        double precipitationAmount = 0.0;
        double snowAmount = 0.0;
        int precipitationProbability = 0;

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        int nowHour = now.getHour() * 100; // 예: 9시 -> 900, 14시 -> 1400

        for (WeatherApiResponseDto.Item item : items) {
            if (!item.getFcstDate().equals(targetDate)) continue;

            String category = item.getCategory();
            String time = item.getFcstTime();
            int timeInt = Integer.parseInt(time);
            String value = item.getFcstValue();

            boolean isTargetTime = false;
            if (tomorrow) {
                // 내일 추천은 무조건 09~24시
                isTargetTime = (timeInt >= 900 && timeInt <= 2400);
            } else {
                // 오늘 추천
                if (nowHour < 900) {
                    isTargetTime = (timeInt >= 900 && timeInt <= 2400);
                } else {
                    isTargetTime = (timeInt >= nowHour && timeInt <= 2400);
                }
            }

            switch (category) {
                case "TMP":
                    int temp = Integer.parseInt(value);
                    hourlyTemperatures.add(new HourlyTemperatureDto(time, temp));
                    if (isTargetTime) {
                        tempsForAverage.add(temp);
                    }
                    maxTemp = Math.max(maxTemp, temp);
                    minTemp = Math.min(minTemp, temp);
                    break;
                case "SKY":
                    if (time.equals("0900")) {
                        weatherType = getSkyDescription(value);
                    }
                    break;
                case "PTY":
                    if (time.equals("0900") && !value.equals("0")) {
                        weatherType = getPrecipitationDescription(value);
                    }
                    break;
                case "POP":
                    if (time.equals("0900")) {
                        precipitationProbability = Integer.parseInt(value);
                    }
                    break;
                case "PCP":
                    if (time.equals("0900")) {
                        precipitationAmount = parsePrecipitationOrSnow(value);
                    }
                    break;
                case "SNO":
                    if (time.equals("0900")) {
                        snowAmount = parsePrecipitationOrSnow(value);
                    }
                    break;
            }
        }

        // ⭐ 평균 계산 (반올림 + int 변환)
        double averageTemp = tempsForAverage.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        return ForecastDto.builder()
                .maxTemperature(maxTemp)
                .minTemperature(minTemp)
                .hourlyTemperatures(hourlyTemperatures)
                .weatherType(weatherType)
                .precipitationAmount(precipitationAmount)
                .snowAmount(snowAmount)
                .precipitationProbability(precipitationProbability)
                .averageTemperature((int) Math.round(averageTemp)) // 🔥 여기만 int로 변환해서 넘기기
                .build();
    }

    private double parsePrecipitationOrSnow(String value) {
        if (value.equals("강수없음") || value.equals("적설없음")) {
            return 0.0;
        } else if (value.endsWith("mm")) {
            return Double.parseDouble(value.replace("mm", "").trim());
        } else if (value.endsWith("cm")) {
            return Double.parseDouble(value.replace("cm", "").trim());
        }
        return 0.0;
    }

    private String getSkyDescription(String skyValue) {
        switch (skyValue) {
            case "1": return "맑음";
            case "3": return "구름많음";
            case "4": return "흐림";
            default: return "맑음";
        }
    }

    private String getPrecipitationDescription(String ptyValue) {
        switch (ptyValue) {
            case "1": return "비";
            case "2": return "비/눈";
            case "3": return "눈";
            case "4": return "소나기";
            default: return "맑음";
        }
    }
}
