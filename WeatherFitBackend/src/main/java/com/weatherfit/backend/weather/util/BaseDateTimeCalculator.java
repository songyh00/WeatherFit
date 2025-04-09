package com.weatherfit.backend.weather.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BaseDateTimeCalculator {

    public static String getBaseDate(boolean tomorrow) {
        LocalDate now = LocalDate.now();
        if (tomorrow) {
            now = now.plusDays(1);
        }
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String getToday() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String getBaseTime() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.of(2, 10))) return "2300";
        if (now.isBefore(LocalTime.of(5, 10))) return "0200";
        if (now.isBefore(LocalTime.of(8, 10))) return "0500";
        if (now.isBefore(LocalTime.of(11, 10))) return "0800";
        if (now.isBefore(LocalTime.of(14, 10))) return "1100";
        if (now.isBefore(LocalTime.of(17, 10))) return "1400";
        if (now.isBefore(LocalTime.of(20, 10))) return "1700";
        if (now.isBefore(LocalTime.of(23, 10))) return "2000";
        return "2300";
    }
}
