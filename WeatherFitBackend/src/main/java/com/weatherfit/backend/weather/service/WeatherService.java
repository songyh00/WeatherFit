package com.weatherfit.backend.weather.service;

import com.weatherfit.backend.weather.dto.HourlyWeatherDto;
import com.weatherfit.backend.weather.dto.WeatherForecastDto;
import com.weatherfit.backend.weather.util.BaseDateTimeCalculator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String weatherApiKey;

    private static final String WEATHER_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

    public WeatherForecastDto getForecast(int nx, int ny, boolean tomorrow) {
        String baseDate = BaseDateTimeCalculator.getBaseDate(false);
        String baseTime = BaseDateTimeCalculator.getBaseTime();

        String apiUrl = WEATHER_URL
                + "?serviceKey=" + weatherApiKey
                + "&numOfRows=100&pageNo=1&dataType=JSON"
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime
                + "&nx=" + nx
                + "&ny=" + ny;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        WeatherForecastDto allData = parseForecastResponse(response);
        return filterForecast(allData, tomorrow);
    }

    private WeatherForecastDto parseForecastResponse(String response) {
        JSONObject json = new JSONObject(response);
        JSONArray items = json.getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");

        List<HourlyWeatherDto> hourlyList = new ArrayList<>();
        double minTemp = Double.MAX_VALUE;
        double maxTemp = Double.MIN_VALUE;

        // 임시 저장
        String[] weathers = new String[items.length()];
        Double[] temps = new Double[items.length()];
        String[] times = new String[items.length()];

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String category = item.getString("category");
            String fcstTime = item.getString("fcstTime");
            String fcstValue = item.getString("fcstValue");

            times[i] = fcstTime;

            if (category.equals("TMP")) { // 온도
                double temp = Double.parseDouble(fcstValue);
                temps[i] = temp;
                minTemp = Math.min(minTemp, temp);
                maxTemp = Math.max(maxTemp, temp);
            } else if (category.equals("PTY")) { // 강수 형태
                weathers[i] = convertPtyToWeather(Integer.parseInt(fcstValue));
            } else if (category.equals("SKY")) { // 하늘 상태
                if (weathers[i] == null || weathers[i].isEmpty()) {
                    weathers[i] = convertSkyToWeather(Integer.parseInt(fcstValue));
                }
            }
        }

        // 시간별로 다시 정리
        for (int i = 0; i < items.length(); i++) {
            if (times[i] != null && temps[i] != null && weathers[i] != null) {
                hourlyList.add(new HourlyWeatherDto(times[i], temps[i], weathers[i]));
            }
        }

        WeatherForecastDto forecastDto = new WeatherForecastDto();
        forecastDto.setDate(BaseDateTimeCalculator.getToday());
        forecastDto.setMinTemperature(minTemp);
        forecastDto.setMaxTemperature(maxTemp);
        forecastDto.setWeatherList(hourlyList);

        return forecastDto;
    }

    private String convertPtyToWeather(int pty) {
        return switch (pty) {
            case 1 -> "비";
            case 2 -> "비/눈";
            case 3 -> "눈";
            default -> "";
        };
    }

    private String convertSkyToWeather(int sky) {
        return switch (sky) {
            case 1 -> "맑음";
            case 3 -> "구름많음";
            case 4 -> "흐림";
            default -> "";
        };
    }

    private WeatherForecastDto filterForecast(WeatherForecastDto original, boolean tomorrow) {
        List<HourlyWeatherDto> filteredList = new ArrayList<>();

        if (tomorrow) {
            for (HourlyWeatherDto h : original.getWeatherList()) {
                int hour = Integer.parseInt(h.getForecastTime().substring(0, 2));
                if (hour >= 5 || hour == 2) {
                    filteredList.add(h);
                }
            }
        } else {
            int nowHour = LocalTime.now().getHour();
            for (HourlyWeatherDto h : original.getWeatherList()) {
                int hour = Integer.parseInt(h.getForecastTime().substring(0, 2));
                if (nowHour <= hour || hour <= 2) {
                    filteredList.add(h);
                }
            }
        }

        WeatherForecastDto filtered = new WeatherForecastDto();
        filtered.setDate(original.getDate());
        filtered.setMinTemperature(original.getMinTemperature());
        filtered.setMaxTemperature(original.getMaxTemperature());
        filtered.setWeatherList(filteredList);

        return filtered;
    }
}
