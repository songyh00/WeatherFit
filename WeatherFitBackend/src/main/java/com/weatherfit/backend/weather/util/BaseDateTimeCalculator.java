package com.weatherfit.backend.weather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseDateTimeCalculator {

    public static class DateTimeInfo {
        private String baseDate;
        private String baseTime;
        private String targetDate;

        public DateTimeInfo(String baseDate, String baseTime, String targetDate) {
            this.baseDate = baseDate;
            this.baseTime = baseTime;
            this.targetDate = targetDate;
        }

        public String getBaseDate() { return baseDate; }
        public String getBaseTime() { return baseTime; }
        public String getTargetDate() { return targetDate; }
    }

    public static DateTimeInfo getForecastDateTime(boolean tomorrow) {
        LocalDateTime now = LocalDateTime.now();

        // 발표 기준 시간 (3시간 간격)
        int[] baseHours = {2, 5, 8, 11, 14, 17, 20, 23};

        int hour = now.getHour();
        int minute = now.getMinute();
        int selectedHour = 2;

        for (int h : baseHours) {
            if (hour < h || (hour == h && minute < 70)) {  // ⭐ 발표시간 + 1시간(60분) + 여유 10분 = 70분
                break;
            }
            selectedHour = h;
        }

        // 날짜가 바뀌어야 하는 경우
        if (hour < 2 || (hour == 2 && minute < 70)) {
            now = now.minusDays(1);
        }

        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = String.format("%02d00", selectedHour);
        String targetDate;

        if (tomorrow) {
            targetDate = now.plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        } else {
            targetDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        return new DateTimeInfo(baseDate, baseTime, targetDate);
    }
}
