package com.weatherfit.backend.weather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 기상청 API 요청을 위한 기준 날짜와 시간 계산기
 *
 * (현재 시각을 기준으로 API 요청에 필요한 base_date, base_time을 계산하고,
 * 요청 실패 시 직전 사이클로 이동하는 기능 제공)
 */
public class BaseDateTimeCalculator {

    /**
     * 현재 시각을 기준으로 기상청 API에 사용할 base_date, base_time을 계산한다.
     *
     * <p>
     * 기상청은 매 3시간마다(02시, 05시, 08시, 11시, 14시, 17시, 20시, 23시) 데이터를 발표한다.
     * 발표 직후 데이터가 완전히 준비되기까지 약 40~45분이 걸리므로,
     * 현재 시각이 '45분 이전'이면 아직 새로운 데이터가 준비되지 않았을 가능성이 있다.
     * 이 경우 한 사이클 전(base_time - 3시간) 데이터를 요청하여 안정적으로 데이터를 가져온다.
     * </p>
     *
     * @return 기준 날짜와 시간 (DateTime 객체)
     */
    public static DateTime calculateBaseDateTime() {
        LocalDateTime now = LocalDateTime.now();
        int minute = now.getMinute();
        int hour = now.getHour();

        // 45분 미만이면 데이터 준비가 완료되지 않았을 가능성이 높아 한 사이클 전 시간으로 조정
        if (minute < 45) {
            hour -= 1;
        }

        // 현재 시간(hour)에 따라 가장 가까운 발표 시간으로 보정
        if (hour < 2) {
            now = now.minusDays(1); // 2시 이전이면 전날 23시 데이터 사용
            hour = 23;
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

        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = String.format("%02d00", hour); // 예: "0200", "0500", "0800" 형식

        return new DateTime(baseDate, baseTime);
    }

    /**
     * 기존 기준 날짜/시간에서 직전 사이클로 이동한다.
     *
     * <p>
     * 요청한 데이터가 실패했을 경우(예: 새 데이터가 아직 준비 안 됨),
     * 바로 직전 3시간 전 발표 시간대로 fallback하여 다시 요청하기 위한 메서드이다.
     * </p>
     *
     * <p>
     * 예시:
     * <ul>
     * <li>0500 요청 실패 → 0200으로 fallback</li>
     * <li>0200 요청 실패 → 전날 2300으로 fallback</li>
     * </ul>
     * </p>
     *
     * @param current 현재 기준 날짜와 시간
     * @return 직전 사이클의 날짜와 시간
     */
    public static DateTime calculatePreviousDateTime(DateTime current) {
        int hour = Integer.parseInt(current.getBaseTime().substring(0, 2));

        // 3시간 빼기
        hour -= 3;

        // 날짜 변환을 위한 LocalDateTime 생성
        LocalDateTime dateTime = LocalDateTime.parse(current.getBaseDate() + "0000", DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        if (hour < 0) { // 0시 이전이면 전날로 넘어감
            dateTime = dateTime.minusDays(1);
            hour = 23;
        }

        String baseDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = String.format("%02d00", hour);

        return new DateTime(baseDate, baseTime);
    }

    /**
     * 기준 날짜와 시간(baseDate, baseTime)을 담는 내부 클래스
     */
    public static class DateTime {
        private final String baseDate;
        private final String baseTime;

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
