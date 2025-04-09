package com.weatherfit.backend.weather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseDateTimeCalculator {

    // 기준 날짜와 시간 계산 (기상청 API 기준)
    public static DateTime calculateBaseDateTime() {
        LocalDateTime now = LocalDateTime.now();
        int minute = now.getMinute();

        // 기상청 API는 매 3시간마다 자료 갱신: 02시, 05시, 08시, 11시, 14시, 17시, 20시, 23시
        int hour = now.getHour();

        // 분이 45분 미만이면 시간 하나 전으로 설정
        if (minute < 45) {
            hour -= 1;
        }

        // 시간 범위에 맞춰서 기준 시간 계산
        if (hour < 2) {
            now = now.minusDays(1); // 2시 이전이면 하루 전으로
            hour = 23; // 23시로 설정
        } else if (hour < 5) {
            hour = 2;
        } else if (hour < 8) {
            hour = 5;
        } else if (hour < 11) {
            hour = 8;
        } else if (hour < 14) {
            hour = 11;
        } else if (hour < 17) {
            hour = 14;
        } else if (hour < 20) {
            hour = 17;
        } else if (hour < 23) {
            hour = 20;
        } else {
            hour = 23;
        }

        // 기준 날짜와 시간 포맷 설정
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = String.format("%02d00", hour); // 예: "0200", "0500", ...

        return new DateTime(baseDate, baseTime);
    }

    // 날짜와 시간을 담는 내부 클래스
    public static class DateTime {
        private String baseDate;
        private String baseTime;

        public DateTime(String baseDate, String baseTime) {
            this.baseDate = baseDate;
            this.baseTime = baseTime;
        }

        public String getBaseDate() {
            return baseDate;
        }

        public String getBaseTime() {
            return baseTime;
        }
    }
}
