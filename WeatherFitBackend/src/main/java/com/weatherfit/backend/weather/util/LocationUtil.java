package com.weatherfit.backend.weather.util;

/**
 * 위도/경도를 기상청 초단기/단기예보 API용 격자 좌표(nx, ny)로 변환하는 유틸리티 클래스
 */
public class LocationUtil {

    // 고정된 변환 파라미터 (기상청 공식 표준값)
    private static final double RE = 6371.00877; // 지구 반경 (km)
    private static final double GRID = 5.0;      // 격자 간격 (km)
    private static final double SLAT1 = 30.0;    // 투영 위도 1 (도)
    private static final double SLAT2 = 60.0;    // 투영 위도 2 (도)
    private static final double OLON = 126.0;    // 기준 경도 (도)
    private static final double OLAT = 38.0;     // 기준 위도 (도)
    private static final double XO = 43;         // 기준 X좌표 (격자)
    private static final double YO = 136;        // 기준 Y좌표 (격자)

    /**
     * 위도(lat), 경도(lng)를 입력받아
     * 기상청 격자 좌표계(nx, ny)로 변환한다.
     *
     * @param lat 위도 (Latitude)
     * @param lng 경도 (Longitude)
     * @return 변환된 격자 좌표 (XY 객체)
     */
    public static XY xyFromLatLng(double lat, double lng) {
        final double DEGRAD = Math.PI / 180.0; // 도 -> 라디안 변환 상수

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        // 표준 위도 1, 2를 기반으로 계산하는 변환 상수
        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);

        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;

        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        // 입력 위도(lat), 경도(lng) 변환
        double ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);

        double theta = lng * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        // 최종 격자 좌표 계산
        int x = (int) (ra * Math.sin(theta) + XO + 0.5);
        int y = (int) (ro - ra * Math.cos(theta) + YO + 0.5);

        return new XY(x, y);
    }

    /**
     * 격자 좌표 (nx, ny)를 담는 내부 클래스
     */
    public static class XY {
        public int x; // 격자 X 좌표
        public int y; // 격자 Y 좌표

        public XY(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}