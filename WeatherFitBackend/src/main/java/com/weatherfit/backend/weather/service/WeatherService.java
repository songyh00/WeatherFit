package com.weatherfit.backend.weather.service;

import com.weatherfit.backend.weather.dto.HourlyWeatherDto;
import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import com.weatherfit.backend.weather.util.BaseDateTimeCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WeatherService {

    @Value("${weather.api.url}")
    private String weatherApiUrl; // 기상 API URL

    @Value("${weather.api.key}")
    private String weatherApiKey; // 기상 API 키

    private final RestTemplate restTemplate = new RestTemplate();

    // 날씨 정보를 조회하고 WeatherForecastDto로 반환
    public WeatherForecastDto getForecast(int nx, int ny, String forecastType) {
        // 기준 날짜와 시간 계산
        BaseDateTimeCalculator.DateTime baseDateTime = BaseDateTimeCalculator.calculateBaseDateTime();

        // 날씨 API 호출 URL 구성
        String url = weatherApiUrl + "/getVilageFcst"
                + "?serviceKey=" + weatherApiKey
                + "&numOfRows=1500"
                + "&pageNo=1"
                + "&dataType=JSON"
                + "&base_date=" + baseDateTime.getBaseDate()
                + "&base_time=" + baseDateTime.getBaseTime()
                + "&nx=" + nx
                + "&ny=" + ny;

        // API 호출
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // 응답에서 날씨 데이터 파싱
        JSONObject json = new JSONObject(response.getBody());
        JSONArray items = json.getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");

        // 파싱된 데이터를 HourlyWeatherDto 목록으로 변환
        List<HourlyWeatherDto> hourlyWeathers = parseWeather(items, forecastType);

        // 최저 기온과 최고 기온 계산
        int minTemp = hourlyWeathers.stream().mapToInt(HourlyWeatherDto::getTemperature).min().orElse(0);
        int maxTemp = hourlyWeathers.stream().mapToInt(HourlyWeatherDto::getTemperature).max().orElse(0);

        // WeatherForecastDto 반환
        return new WeatherForecastDto(minTemp, maxTemp, hourlyWeathers);
    }

    // 날씨 데이터 파싱
    private List<HourlyWeatherDto> parseWeather(JSONArray items, String forecastType) {
        // 카테고리별 데이터 저장 맵
        Map<String, String> tempMap = new HashMap<>();
        Map<String, String> skyMap = new HashMap<>();
        Map<String, String> ptyMap = new HashMap<>();
        Map<String, String> pcpMap = new HashMap<>();
        Map<String, String> snoMap = new HashMap<>();

        // API 응답 항목 파싱
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String category = item.getString("category");
            String fcstDate = item.getString("fcstDate");
            String fcstTime = item.getString("fcstTime");
            String value = item.getString("fcstValue");

            String dateTimeKey = fcstDate + fcstTime;

            // 각 카테고리별로 데이터 저장
            switch (category) {
                case "TMP": tempMap.put(dateTimeKey, value); break;
                case "SKY": skyMap.put(dateTimeKey, value); break;
                case "PTY": ptyMap.put(dateTimeKey, value); break;
                case "PCP": pcpMap.put(dateTimeKey, value); break;
                case "SNO": snoMap.put(dateTimeKey, value); break;
            }
        }

        // 현재 시간과 비교하여 날씨 데이터 필터링
        LocalDateTime now = LocalDateTime.now();
        List<HourlyWeatherDto> result = new ArrayList<>();

        for (String dateTimeKey : tempMap.keySet()) {
            LocalDateTime targetTime = parseDateTime(dateTimeKey);

            if (isTargetTime(targetTime, now, forecastType)) {
                // 각 데이터를 HourlyWeatherDto 객체로 변환
                int temp = Integer.parseInt(tempMap.get(dateTimeKey));
                String sky = convertSkyCodeToText(skyMap.getOrDefault(dateTimeKey, "1"));
                String pty = ptyMap.getOrDefault(dateTimeKey, "0");
                String weatherDescription = getWeatherDescription(sky, pty);
                String precipitation = getPrecipitation(pty, pcpMap.get(dateTimeKey), snoMap.get(dateTimeKey));

                result.add(new HourlyWeatherDto(targetTime, temp, weatherDescription, precipitation));
            }
        }

        // 시간 순으로 정렬
        result.sort(Comparator.comparing(HourlyWeatherDto::getDateTime));
        return result;
    }

    // 예보 시간 필터링
    private boolean isTargetTime(LocalDateTime targetTime, LocalDateTime now, String forecastType) {
        if (forecastType.equals("today")) {
            LocalDateTime todayEnd = now.toLocalDate().atTime(23, 59);
            LocalDateTime tomorrow2am = now.toLocalDate().plusDays(1).atTime(2, 0);
            return !targetTime.isBefore(now) && !targetTime.isAfter(tomorrow2am);
        } else if (forecastType.equals("tomorrow")) {
            LocalDateTime tomorrow5am = now.toLocalDate().plusDays(1).atTime(5, 0);
            LocalDateTime dayAfterTomorrow2am = now.toLocalDate().plusDays(2).atTime(2, 0);
            return !targetTime.isBefore(tomorrow5am) && !targetTime.isAfter(dayAfterTomorrow2am);
        }
        return false;
    }

    // 날짜/시간 문자열을 LocalDateTime 객체로 변환
    private LocalDateTime parseDateTime(String dateTimeKey) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.parse(dateTimeKey, formatter);
    }

    // 하늘 상태 코드 변환
    private String convertSkyCodeToText(String code) {
        switch (code) {
            case "1": return "맑음";
            case "3": return "구름많음";
            case "4": return "흐림";
            default: return "알수없음";
        }
    }

    // 날씨 상태 설명 생성
    private String getWeatherDescription(String sky, String pty) {
        switch (pty) {
            case "1": return "비";
            case "2": return "비/눈";
            case "3": return "눈";
            case "4": return "소나기";
            default: return sky; // PTY가 0이면 SKY 텍스트 사용
        }
    }

    // 강수량/적설량 반환
    private String getPrecipitation(String pty, String pcp, String sno) {
        if (pty.equals("1") || pty.equals("2") || pty.equals("4")) { // 비
            if (pcp != null && !pcp.equals("강수없음")) {
                return pcp;
            }
        } else if (pty.equals("3")) { // 눈
            if (sno != null && !sno.equals("적설없음")) {
                return sno;
            }
        }
        return null;
    }
}
