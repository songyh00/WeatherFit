package com.weatherfit.backend.weather.dto;

import lombok.Data;

@Data
public class WeatherApiResponseDto {
    private Response response;

    @Data
    public static class Response {
        private Body body;
    }

    @Data
    public static class Body {
        private Items items;
    }

    @Data
    public static class Items {
        private java.util.List<Item> item;
    }

    @Data
    public static class Item {
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;
    }
}
