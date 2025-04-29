package com.weatherfit.backend.weather.service;

import com.weatherfit.backend.weather.dto.ForecastDto;
import com.weatherfit.backend.weather.dto.HourlyTemperatureDto;
import com.weatherfit.backend.weather.dto.WeatherApiResponseDto;
import com.weatherfit.backend.weather.dto.WeatherResponseDto;
import com.weatherfit.backend.weather.util.BaseDateTimeCalculator;
import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WebClient webClient;

    @Value("${weather.api.service-key}")
    private String serviceKey;

    /**
     * 코디 추천용 날씨 데이터 조회 (평균 온도만 반환)
     */
    public ForecastDto getForecastForClothing(int nx, int ny, boolean tomorrow) {
        log.info("🧥 코디 추천용 날씨 예보 조회 요청: nx={}, ny={}, tomorrow={}", nx, ny, tomorrow);
        BaseDateTimeCalculator.DateTimeInfo dateTimeInfo = BaseDateTimeCalculator.getForecastDateTime(tomorrow);
        WeatherApiResponseDto responseDto = fetchWeatherApi(nx, ny, dateTimeInfo);
        return parseForecastDataForClothing(responseDto, dateTimeInfo.getTargetDate(), tomorrow);
    }

    /**
     * 날씨 예보용 데이터 조회 (시간별+최고/최저 기온 반환)
     */
    public WeatherResponseDto getForecastForWeather(int nx, int ny, boolean tomorrow) {
        log.info("🌦️ 날씨 예보용 데이터 조회 요청: nx={}, ny={}, tomorrow={}", nx, ny, tomorrow);
        BaseDateTimeCalculator.DateTimeInfo dateTimeInfo = BaseDateTimeCalculator.getForecastDateTime(tomorrow);
        WeatherApiResponseDto responseDto = fetchWeatherApi(nx, ny, dateTimeInfo);
        return parseForecastDataForWeather(responseDto, dateTimeInfo.getTargetDate(), tomorrow);
    }

    /**
     * 기상청 API 호출
     */
    private WeatherApiResponseDto fetchWeatherApi(int nx, int ny, BaseDateTimeCalculator.DateTimeInfo dateTimeInfo) {
        try {
            return webClient.get()
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
        } catch (Exception e) {
            log.error("🔴 날씨 API 호출 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }

    /**
     * 옷 코디 추천용 데이터 파싱 (평균 기온만)
     */
    private ForecastDto parseForecastDataForClothing(WeatherApiResponseDto responseDto, String targetDate, boolean tomorrow) {
        List<WeatherApiResponseDto.Item> items = responseDto.getResponse().getBody().getItems().getItem();

        List<Integer> temps = new ArrayList<>();

        for (WeatherApiResponseDto.Item item : items) {
            if (!item.getFcstDate().equals(targetDate)) continue;

            String category = item.getCategory();
            int timeInt = Integer.parseInt(item.getFcstTime());

            if (!isClothingRecommendationTargetTime(timeInt, tomorrow)) continue;

            if ("TMP".equals(category)) {
                temps.add(Integer.parseInt(item.getFcstValue()));
            }
        }

        double averageTemp = temps.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        return ForecastDto.builder()
                .averageTemperature((int) Math.round(averageTemp))
                .build();
    }

    /**
     * 날씨 예보용 데이터 파싱 (시간별 + 최고/최저 온도)
     */
    private WeatherResponseDto parseForecastDataForWeather(WeatherApiResponseDto responseDto, String targetDate, boolean tomorrow) {
        List<WeatherApiResponseDto.Item> items = responseDto.getResponse().getBody().getItems().getItem();

        Map<String, HourlyTemperatureDto> hourlyMap = new HashMap<>();

        for (WeatherApiResponseDto.Item item : items) {
            if (!item.getFcstDate().equals(targetDate)) continue;

            String category = item.getCategory();
            int timeInt = Integer.parseInt(item.getFcstTime());

            if (!isForecastTargetTime(timeInt, tomorrow)) continue;

            String month = item.getFcstDate().substring(4, 6);
            String day = item.getFcstDate().substring(6, 8);
            String time = item.getFcstTime();

            HourlyTemperatureDto dto = hourlyMap.getOrDefault(time, new HourlyTemperatureDto(
                    month, day, time, 0, "맑음", 0, 0.0, 0.0
            ));

            switch (category) {
                case "TMP":
                    dto.setTemperature(Integer.parseInt(item.getFcstValue()));
                    break;
                case "SKY":
                    dto.setWeatherType(getSkyDescription(item.getFcstValue()));
                    break;
                case "PTY":
                    if (!"0".equals(item.getFcstValue())) {
                        dto.setWeatherType(getPrecipitationDescription(item.getFcstValue()));
                    }
                    break;
                case "POP":
                    dto.setPrecipitationProbability(Integer.parseInt(item.getFcstValue()));
                    break;
                case "PCP":
                    dto.setPrecipitationAmount(parsePrecipitationOrSnow(item.getFcstValue()));
                    break;
                case "SNO":
                    dto.setSnowAmount(parsePrecipitationOrSnow(item.getFcstValue()));
                    break;
            }

            hourlyMap.put(time, dto);
        }

        List<HourlyTemperatureDto> hourlyTemperatures = new ArrayList<>(hourlyMap.values());
        hourlyTemperatures.sort(Comparator.comparing(HourlyTemperatureDto::getTime));

        OptionalInt maxTemp = hourlyTemperatures.stream()
                .mapToInt(HourlyTemperatureDto::getTemperature)
                .max();

        OptionalInt minTemp = hourlyTemperatures.stream()
                .mapToInt(HourlyTemperatureDto::getTemperature)
                .min();

        return WeatherResponseDto.builder()
                .hourlyTemperatures(hourlyTemperatures)
                .maxTemperature(maxTemp.orElse(0))
                .minTemperature(minTemp.orElse(0))
                .build();
    }

    /**
     * 날씨 예보용 시간 필터
     */
    private boolean isForecastTargetTime(int timeInt, boolean tomorrow) {
        if (tomorrow) {
            return (timeInt >= 0 && timeInt < 2400);
        } else {
            LocalDateTime now = LocalDateTime.now();
            int nowHour = now.getHour() * 100;
            return (timeInt >= nowHour && timeInt < 2400);
        }
    }

    /**
     * 코디 추천용 시간 필터
     */
    private boolean isClothingRecommendationTargetTime(int timeInt, boolean tomorrow) {
        LocalDateTime now = LocalDateTime.now();
        int nowHour = now.getHour() * 100;
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