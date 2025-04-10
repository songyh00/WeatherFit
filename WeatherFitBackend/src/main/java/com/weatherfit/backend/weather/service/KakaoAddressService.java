package com.weatherfit.backend.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 주소를 위도/경도로 변환하는 서비스
 */
@Service
public class KakaoAddressService {

    @Value("${kakaomap.api.key}")
    private String kakaoApiKey;  // 카카오 API 키

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 주소를 입력받아 해당 주소의 위도, 경도를 반환한다.
     *
     * @param address 검색할 주소
     * @return [위도, 경도] 배열
     */
    public double[] getCoordinates(String address) {
        // 1. 카카오 로컬 API 호출 URL 생성
        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

        // 2. HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        // 3. HTTP 요청 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 4. API 호출 및 응답 처리
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // 5. 응답 JSON 파싱
        JSONObject json = new JSONObject(response.getBody());
        JSONArray documents = json.getJSONArray("documents");

        // 6. 결과가 없으면 예외 처리
        if (documents.isEmpty()) {
            throw new RuntimeException("주소를 찾을 수 없습니다: " + address);
        }

        // 7. 첫 번째 결과에서 위도와 경도 추출
        JSONObject firstResult = documents.getJSONObject(0);
        double latitude = firstResult.getDouble("y"); // 위도
        double longitude = firstResult.getDouble("x"); // 경도

        return new double[]{latitude, longitude};
    }
}
