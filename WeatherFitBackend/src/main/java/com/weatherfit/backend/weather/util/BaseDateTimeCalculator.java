package com.weatherfit.backend.weather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 기상청 단기예보 API용 날짜(baseDate), 시간(baseTime), 예측 날짜(targetDate)를 계산하는 유틸리티
 */
public class BaseDateTimeCalculator {

    /**
     * baseDate, baseTime, targetDate를 담는 DTO 클래스
     */
    public static class DateTimeInfo {
        private final String baseDate;   // API 요청 기준 날짜 (yyyyMMdd)
        private final String baseTime;   // API 요청 기준 시간 (HH00)
        private final String targetDate; // 실제 조회하고 싶은 날짜 (yyyyMMdd)

        public DateTimeInfo(String baseDate, String baseTime, String targetDate) {
            this.baseDate = baseDate;
            this.baseTime = baseTime;
            this.targetDate = targetDate;
        }

        public String getBaseDate() { return baseDate; }
        public String getBaseTime() { return baseTime; }
        public String getTargetDate() { return targetDate; }
    }

    /**
     * 오늘/내일 여부를 기준으로 기상청 API에 사용할 날짜, 시간 계산
     */
    public static DateTimeInfo getForecastDateTime(boolean tomorrow) {
        LocalDateTime now = LocalDateTime.now();

        // 기상청 API는 3시간 단위로 데이터 발표
        int[] baseHours = {2, 5, 8, 11, 14, 17, 20, 23};

        int hour = now.getHour();
        int minute = now.getMinute();
        int selectedHour = 2; // 기본은 02시

        // 현재 시간에 맞춰 가장 가까운 baseTime을 선택
        for (int h : baseHours) {
            if (hour < h || (hour == h && minute < 70)) {  // 발표 후 1시간 10분 이내까지만 같은 baseTime 사용
                break;
            }
            selectedHour = h;
        }

        // 새벽 00~01시라면 전날로 간주
        if (hour < 2 || (hour == 2 && minute < 70)) {
            now = now.minusDays(1);
        }

        // 최종 baseDate와 baseTime
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = String.format("%02d00", selectedHour);

        // targetDate는 오늘 or 내일
        String targetDate = tomorrow
                ? now.plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                : now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return new DateTimeInfo(baseDate, baseTime, targetDate);
    }
}
