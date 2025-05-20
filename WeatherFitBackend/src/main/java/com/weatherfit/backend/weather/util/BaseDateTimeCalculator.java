package com.weatherfit.backend.weather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 기상청 단기예보 API 요청용 날짜(baseDate), 시간(baseTime), 예측 날짜(targetDate)를 계산하는 유틸리티 클래스
 */
public class BaseDateTimeCalculator {

    private static final DateTimeFormatter FCST_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    /**
     * baseDate, baseTime, targetDate를 담는 DTO 클래스
     * - baseDate : API 호출 기준 날짜
     * - baseTime : API 호출 기준 시간
     * - targetDate : 실제 조회하고 싶은 날짜
     */
    public static class DateTimeInfo {
        private final String baseDate;
        private final String baseTime;
        private final String targetDate;

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
     * 오늘/내일 여부를 기준으로 기상청 API에 사용할 baseDate, baseTime, targetDate를 계산
     *
     * @param tomorrow 내일 조회 여부 (true: 내일, false: 오늘)
     * @return DateTimeInfo (baseDate, baseTime, targetDate)
     */
    public static DateTimeInfo getForecastDateTime(boolean tomorrow) {
        LocalDateTime now = LocalDateTime.now();

        // 기상청 단기예보 API는 하루 8번 발표: 02, 05, 08, 11, 14, 17, 20, 23시
        int[] baseHours = {2, 5, 8, 11, 14, 17, 20, 23};
        int hour = now.getHour();
        int minute = now.getMinute();
        int selectedHour = 2; // 기본값: 02시

        // 3시간마다 발표된 가장 가까운 baseTime 선택
        for (int h : baseHours) {
            if (hour < h || (hour == h && minute < 70)) {
                break;
            }
            selectedHour = h;
        }

        // 새벽 0~2시 (02시 발표 전) 요청은 전날 데이터 기준
        if (hour < 2 || (hour == 2 && minute < 70)) {
            now = now.minusDays(1);
        }

        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = String.format("%02d00", selectedHour);

        String targetDate = tomorrow
                ? now.plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                : now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return new DateTimeInfo(baseDate, baseTime, targetDate);
    }

    /**
     * fcstDate + fcstTime 문자열을 LocalDateTime 객체로 변환
     *
     * @param date 예: "20240518"
     * @param time 예: "1400"
     * @return LocalDateTime
     */
    public static LocalDateTime parseFcstDateTime(String date, String time) {
        return LocalDateTime.parse(date + time, FCST_DATE_TIME_FORMATTER);
    }
}
