package com.weatherfit.backend.weather.service;

import com.weatherfit.backend.weather.dto.HourlyWeatherDto;
import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import com.weatherfit.backend.weather.util.BaseDateTimeCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 기상청 단기예보 데이터를 조회하고 가공하는 서비스
 */
@Service
public class WeatherService {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 주어진 좌표(nx, ny)와 예보 타입(today, tomorrow)에 맞는 날씨 데이터를 조회한다.
     */
    public WeatherForecastDto getForecast(int nx, int ny, String forecastType) {
        BaseDateTimeCalculator.DateTime baseDateTime = BaseDateTimeCalculator.calculateBaseDateTime();

        // 1차 시도
        JSONArray items = tryFetchItems(nx, ny, baseDateTime);

        // 실패하면 직전 사이클로 fallback
        if (items == null || items.isEmpty()) {
            BaseDateTimeCalculator.DateTime fallbackDateTime = BaseDateTimeCalculator.calculatePreviousDateTime(baseDateTime);
            items = tryFetchItems(nx, ny, fallbackDateTime);

            if (items == null || items.isEmpty()) {
                throw new RuntimeException("현재 기상 데이터가 준비되지 않았습니다. 잠시 후 다시 시도해주세요.");
            }
        }

        List<HourlyWeatherDto> hourlyWeathers = parseWeather(items, forecastType);

        int minTemp = hourlyWeathers.stream().mapToInt(HourlyWeatherDto::getTemperature).min().orElse(0);
        int maxTemp = hourlyWeathers.stream().mapToInt(HourlyWeatherDto::getTemperature).max().orElse(0);

        return new WeatherForecastDto(minTemp, maxTemp, hourlyWeathers);
    }

    private JSONArray tryFetchItems(int nx, int ny, BaseDateTimeCalculator.DateTime dateTime) {
        try {
            String url = weatherApiUrl + "/getVilageFcst"
                    + "?serviceKey=" + weatherApiKey
                    + "&numOfRows=1500"
                    + "&pageNo=1"
                    + "&dataType=JSON"
                    + "&base_date=" + dateTime.getBaseDate()
                    + "&base_time=" + dateTime.getBaseTime()
                    + "&nx=" + nx
                    + "&ny=" + ny;

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            JSONObject json = new JSONObject(response.getBody());
            JSONObject header = json.getJSONObject("response").getJSONObject("header");

            if (!"00".equals(header.getString("resultCode"))) {
                return null;
            }

            return json.getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items")
                    .getJSONArray("item");

        } catch (Exception e) {
            return null;
        }
    }

    private List<HourlyWeatherDto> parseWeather(JSONArray items, String forecastType) {
        Map<String, String> tempMap = new HashMap<>();
        Map<String, String> skyMap = new HashMap<>();
        Map<String, String> ptyMap = new HashMap<>();
        Map<String, String> pcpMap = new HashMap<>();
        Map<String, String> snoMap = new HashMap<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String category = item.getString("category");
            String fcstDate = item.getString("fcstDate");
            String fcstTime = item.getString("fcstTime");
            String value = item.getString("fcstValue");

            String dateTimeKey = fcstDate + fcstTime;

            switch (category) {
                case "TMP" -> tempMap.put(dateTimeKey, value);
                case "SKY" -> skyMap.put(dateTimeKey, value);
                case "PTY" -> ptyMap.put(dateTimeKey, value);
                case "PCP" -> pcpMap.put(dateTimeKey, value);
                case "SNO" -> snoMap.put(dateTimeKey, value);
            }
        }

        LocalDateTime now = LocalDateTime.now();
        List<HourlyWeatherDto> result = new ArrayList<>();

        for (String dateTimeKey : tempMap.keySet()) {
            LocalDateTime targetTime = parseDateTime(dateTimeKey);

            if (isTargetTime(targetTime, now, forecastType)) {
                int temp = Integer.parseInt(tempMap.get(dateTimeKey));
                String sky = convertSkyCodeToText(skyMap.getOrDefault(dateTimeKey, "1"));
                String pty = ptyMap.getOrDefault(dateTimeKey, "0");
                String weatherDescription = getWeatherDescription(sky, pty);
                String precipitation = getPrecipitation(pty, pcpMap.get(dateTimeKey), snoMap.get(dateTimeKey));

                result.add(new HourlyWeatherDto(targetTime, temp, weatherDescription, precipitation));
            }
        }

        result.sort(Comparator.comparing(HourlyWeatherDto::getDateTime));
        return result;
    }

    private boolean isTargetTime(LocalDateTime targetTime, LocalDateTime now, String forecastType) {
        if ("today".equalsIgnoreCase(forecastType)) {
            LocalDateTime todayEnd = now.toLocalDate().atTime(23, 59);
            LocalDateTime tomorrow2am = now.toLocalDate().plusDays(1).atTime(2, 0);
            return !targetTime.isBefore(now) && !targetTime.isAfter(tomorrow2am);
        } else if ("tomorrow".equalsIgnoreCase(forecastType)) {
            LocalDateTime tomorrow5am = now.toLocalDate().plusDays(1).atTime(5, 0);
            LocalDateTime dayAfterTomorrow2am = now.toLocalDate().plusDays(2).atTime(2, 0);
            return !targetTime.isBefore(tomorrow5am) && !targetTime.isAfter(dayAfterTomorrow2am);
        }
        return false;
    }

    private LocalDateTime parseDateTime(String dateTimeKey) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.parse(dateTimeKey, formatter);
    }

    private String convertSkyCodeToText(String code) {
        return switch (code) {
            case "1" -> "맑음";
            case "3" -> "구름많음";
            case "4" -> "흐림";
            default -> "알수없음";
        };
    }

    private String getWeatherDescription(String sky, String pty) {
        return switch (pty) {
            case "1" -> "비";
            case "2" -> "비/눈";
            case "3" -> "눈";
            case "4" -> "소나기";
            default -> sky;
        };
    }

    private String getPrecipitation(String pty, String pcp, String sno) {
        if ("1".equals(pty) || "2".equals(pty) || "4".equals(pty)) {
            if (pcp != null && !"강수없음".equals(pcp)) {
                return pcp;
            }
        } else if ("3".equals(pty)) {
            if (sno != null && !"적설없음".equals(sno)) {
                return sno;
            }
        }
        return null;
    }
}
