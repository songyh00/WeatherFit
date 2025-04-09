package com.weatherfit.backend.weather.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class KakaoAddressService {

    @Value("${kakaomap.api.key}")
    private String kakaoApiKey;

    public double[] getCoordinates(String address) {
        try {
            // ⭐ 주소를 UTF-8로 인코딩
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encodedAddress;

            // RestTemplate 생성
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeaders 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);

            // HttpEntity 생성
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // 응답 상태 코드 확인
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("카카오 API 호출 실패. 상태 코드: " + response.getStatusCode());
            }

            String responseBody = response.getBody();

            // 응답 본문이 비었거나 잘못된 경우
            if (responseBody == null || !responseBody.trim().startsWith("{")) {
                throw new RuntimeException("카카오 API 응답이 비어있거나 JSON 형식이 아닙니다.");
            }

            // JSON 파싱
            JSONObject json = new JSONObject(responseBody);
            var documents = json.getJSONArray("documents");

            // 디버깅 출력
            System.out.println("[DEBUG] 호출 URL: " + apiUrl);
            System.out.println("[DEBUG] Authorization 헤더: KakaoAK " + kakaoApiKey);

            // 결과가 없는 경우
            if (documents.isEmpty()) {
                throw new RuntimeException("카카오 API에서 주소를 찾을 수 없습니다.");
            }

            JSONObject first = documents.getJSONObject(0);
            double longitude = first.getDouble("x"); // 경도
            double latitude = first.getDouble("y");  // 위도

            return new double[]{latitude, longitude};

        } catch (Exception e) {
            // 모든 예외를 감싸서 던짐
            throw new RuntimeException("카카오 주소 변환 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
