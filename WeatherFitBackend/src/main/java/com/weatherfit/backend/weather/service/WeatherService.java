package com.weatherfit.backend.weather.service;

import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;
import com.weatherfit.backend.weather.dto.ForecastDto;
import com.weatherfit.backend.weather.dto.HourlyTemperatureDto;
import com.weatherfit.backend.weather.dto.WeatherApiResponseDto;
import com.weatherfit.backend.weather.dto.WeatherResponseDto;
import com.weatherfit.backend.weather.util.BaseDateTimeCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WebClient webClient;

    @Value("${weather.api.service-key}")
    private String serviceKey;

    /** 캐시 구조 정의 */
    private record CachedWeather<T>(T data, String baseDate, String baseTime, LocalDateTime cachedAt) {
        boolean isSameBase(String date, String time) {
            return this.baseDate.equals(date) && this.baseTime.equals(time);
        }
    }

    /** 캐시 맵 */
    private final Map<String, CachedWeather<WeatherResponseDto>> forecastCache = new ConcurrentHashMap<>();
    private final Map<String, CachedWeather<ForecastDto>> clothingCache = new ConcurrentHashMap<>();

    /**
     * 코디 추천용 날씨 데이터 조회 (평균 온도만 반환)
     */
    public ForecastDto getForecastForClothing(int nx, int ny, boolean tomorrow) {
        log.info("🟡 코디 추천용 날씨 예보 조회 요청: nx={}, ny={}, tomorrow={}", nx, ny, tomorrow);

        BaseDateTimeCalculator.DateTimeInfo dateTimeInfo = BaseDateTimeCalculator.getForecastDateTime(tomorrow);
        String key = nx + "," + ny + "," + tomorrow;

        CachedWeather<ForecastDto> cached = clothingCache.get(key);
        if (cached != null &&
                cached.isSameBase(dateTimeInfo.getBaseDate(), dateTimeInfo.getBaseTime()) &&
                cached.cachedAt().isAfter(LocalDateTime.now().minusHours(6))) {

            log.info("🟢 [옷추천] 기준시간 동일 + 6시간 이내 → 캐시 사용: key={}", key);
            return cached.data();
        } else if (cached != null && !cached.cachedAt().isAfter(LocalDateTime.now().minusHours(6))) {
            clothingCache.remove(key);
            log.info("🟠 [옷추천] 캐시 만료 → 삭제됨: key={}", key);
        }

        try {
            WeatherApiResponseDto responseDto = fetchWeatherApi(nx, ny, dateTimeInfo);
            ForecastDto parsed = parseForecastDataForClothing(responseDto, dateTimeInfo.getTargetDate(), tomorrow);
            clothingCache.put(key, new CachedWeather<>(parsed, dateTimeInfo.getBaseDate(), dateTimeInfo.getBaseTime(), LocalDateTime.now()));
            log.info("🟢 [옷추천] 캐시 저장 완료: key={}, baseTime={}", key, dateTimeInfo.getBaseTime());
            return parsed;
        } catch (Exception e) {
            if (cached != null) {
                log.warn("🔴 [옷추천] API 실패 → 기존 캐시 사용: key={}", key);
                return cached.data();
            }
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }

    /**
     * 날씨 예보용 데이터 조회 (현재 시각부터 내일 23시까지)
     */
    public WeatherResponseDto getForecastFromNowToTomorrowNight(int nx, int ny) {
        log.info("🟡 날씨 예보용 데이터 조회 요청: nx={}, ny={}", nx, ny);

        BaseDateTimeCalculator.DateTimeInfo dateTimeInfo = BaseDateTimeCalculator.getForecastDateTime(false);
        String key = nx + "," + ny;

        CachedWeather<WeatherResponseDto> cached = forecastCache.get(key);
        if (cached != null &&
                cached.isSameBase(dateTimeInfo.getBaseDate(), dateTimeInfo.getBaseTime()) &&
                cached.cachedAt().isAfter(LocalDateTime.now().minusHours(6))) {

            log.info("🟢 [예보] 기준시간 동일 + 6시간 이내 → 캐시 사용: key={}", key);
            return cached.data();
        } else if (cached != null && !cached.cachedAt().isAfter(LocalDateTime.now().minusHours(6))) {
            // 오래된 캐시는 삭제
            forecastCache.remove(key);
            log.info("🟠 [예보] 캐시 만료 → 삭제됨: key={}", key);
        }

        try {
            WeatherApiResponseDto responseDto = fetchWeatherApi(nx, ny, dateTimeInfo);
            WeatherResponseDto parsed = parseForecastFromNowToTomorrow(responseDto);
            forecastCache.put(key, new CachedWeather<>(parsed, dateTimeInfo.getBaseDate(), dateTimeInfo.getBaseTime(), LocalDateTime.now()));
            log.info("🟢 [예보] 캐시 저장 완료: key={}, baseTime={}", key, dateTimeInfo.getBaseTime());
            return parsed;
        } catch (Exception e) {
            if (cached != null) {
                log.warn("🔴 [예보] API 실패 → 기존 캐시 사용: key={}", key);
                return cached.data();
            }
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }

    /**
     * 기상청 API 호출
     */
    private WeatherApiResponseDto fetchWeatherApi(int nx, int ny, BaseDateTimeCalculator.DateTimeInfo dateTimeInfo) {
        try {
            log.info("🟡 기상청 API 호출 시작: baseDate={}, baseTime={}, nx={}, ny={}",
                    dateTimeInfo.getBaseDate(), dateTimeInfo.getBaseTime(), nx, ny);

            WeatherApiResponseDto response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("apis.data.go.kr")
                            .path("/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                            .queryParam("serviceKey", serviceKey)
                            .queryParam("pageNo", 1)
                            .queryParam("numOfRows", 1000)
                            .queryParam("dataType", "JSON")
                            .queryParam("base_date", dateTimeInfo.getBaseDate())
                            .queryParam("base_time", dateTimeInfo.getBaseTime())
                            .queryParam("nx", nx)
                            .queryParam("ny", ny)
                            .build())
                    .retrieve()
                    .bodyToMono(WeatherApiResponseDto.class)
                    .block();

            log.info("🟢 기상청 API 호출 성공");
            return response;
        } catch (Exception e) {
            log.error("🔴 기상청 API 호출 실패: {}", e.getMessage(), e);
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }

    /**
     * 옷 코디 추천용 데이터 파싱 (평균 기온만)
     */
    private ForecastDto parseForecastDataForClothing(WeatherApiResponseDto responseDto, String targetDate, boolean tomorrow) {
        log.info("🟡 코디 추천용 데이터 파싱 시작: targetDate={}, tomorrow={}", targetDate, tomorrow);

        List<Integer> temps = new ArrayList<>();
        List<WeatherApiResponseDto.Item> items = responseDto.getResponse().getBody().getItems().getItem();

        for (WeatherApiResponseDto.Item item : items) {
            if (!item.getFcstDate().equals(targetDate)) continue;
            if (!"TMP".equals(item.getCategory())) continue;

            int timeInt = Integer.parseInt(item.getFcstTime());
            if (!isClothingRecommendationTargetTime(timeInt, tomorrow)) continue;

            temps.add(Integer.parseInt(item.getFcstValue()));
        }

        double averageTemp = temps.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        log.info("🟢 코디 추천용 평균 기온 계산 완료: {}°C", averageTemp);

        return ForecastDto.builder()
                .averageTemperature((int) Math.round(averageTemp))
                .build();
    }

    /**
     * 날씨 예보용 데이터 파싱 (현재 ~ 내일 23시까지) + 시간 순 정렬
     */
    private WeatherResponseDto parseForecastFromNowToTomorrow(WeatherApiResponseDto responseDto) {
        log.info("🟡 일반 날씨 예보 데이터 파싱 시작 (현재~내일 23시)");

        Map<LocalDateTime, HourlyTemperatureDto> hourlyMap = new HashMap<>();
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = now.toLocalDate().plusDays(1).atTime(23, 0);

        for (WeatherApiResponseDto.Item item : responseDto.getResponse().getBody().getItems().getItem()) {
            LocalDateTime forecastDateTime = BaseDateTimeCalculator.parseFcstDateTime(item.getFcstDate(), item.getFcstTime());
            if (forecastDateTime.isBefore(now) || forecastDateTime.isAfter(end)) continue;

            HourlyTemperatureDto dto = hourlyMap.getOrDefault(forecastDateTime, new HourlyTemperatureDto(
                    item.getFcstDate().substring(4, 6),
                    item.getFcstDate().substring(6, 8),
                    item.getFcstTime(), 0, "맑음", 0, 0.0, 0.0
            ));

            switch (item.getCategory()) {
                case "TMP": dto.setTemperature(Integer.parseInt(item.getFcstValue())); break;
                case "SKY": dto.setWeatherType(getSkyDescription(item.getFcstValue())); break;
                case "PTY":
                    if (!"0".equals(item.getFcstValue())) {
                        dto.setWeatherType(getPrecipitationDescription(item.getFcstValue()));
                    }
                    break;
                case "POP": dto.setPrecipitationProbability(Integer.parseInt(item.getFcstValue())); break;
                case "PCP": dto.setPrecipitationAmount(parsePrecipitationOrSnow(item.getFcstValue())); break;
                case "SNO": dto.setSnowAmount(parsePrecipitationOrSnow(item.getFcstValue())); break;
            }

            hourlyMap.put(forecastDateTime, dto);
        }

        List<Map.Entry<LocalDateTime, HourlyTemperatureDto>> sorted = new ArrayList<>(hourlyMap.entrySet());
        sorted.sort(Map.Entry.comparingByKey());

        List<HourlyTemperatureDto> hourlyTemperatures = new ArrayList<>();
        for (Map.Entry<LocalDateTime, HourlyTemperatureDto> entry : sorted) {
            hourlyTemperatures.add(entry.getValue());
        }

        OptionalInt maxTemp = hourlyTemperatures.stream().mapToInt(HourlyTemperatureDto::getTemperature).max();
        OptionalInt minTemp = hourlyTemperatures.stream().mapToInt(HourlyTemperatureDto::getTemperature).min();

        log.info("🟢 시간별 데이터 파싱 완료: 총 {}개", hourlyTemperatures.size());

        return WeatherResponseDto.builder()
                .hourlyTemperatures(hourlyTemperatures)
                .maxTemperature(maxTemp.orElse(0))
                .minTemperature(minTemp.orElse(0))
                .build();
    }

    private boolean isClothingRecommendationTargetTime(int timeInt, boolean tomorrow) {
        int nowHour = LocalDateTime.now().getHour() * 100;
        if (tomorrow) {
            return (timeInt >= 900 && (timeInt <= 2300 || timeInt == 0 || timeInt == 100));
        } else {
            if (nowHour < 900) {
                return (timeInt >= 900 && (timeInt <= 2300 || timeInt == 0 || timeInt == 100));
            } else {
                return (timeInt >= nowHour && (timeInt <= 2300 || timeInt == 0 || timeInt == 100));
            }
        }
    }

    private double parsePrecipitationOrSnow(String value) {
        if (value.equals("강수없음") || value.equals("적설없음")) return 0.0;
        if (value.endsWith("mm")) return Double.parseDouble(value.replace("mm", "").trim());
        if (value.endsWith("cm")) return Double.parseDouble(value.replace("cm", "").trim());
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