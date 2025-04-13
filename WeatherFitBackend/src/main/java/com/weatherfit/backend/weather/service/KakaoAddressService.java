package com.weatherfit.backend.weather.service;

import com.weatherfit.backend.weather.dto.KakaoApiResponseDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 카카오맵 API를 이용해 주소를 위도/경도로 변환하는 서비스
 */
@Slf4j
@Service
public class KakaoAddressService {

    private final WebClient webClient;
    private final String kakaoApiKey;

    /**
     * 생성자 - WebClient와 카카오 API 키 주입
     *
     * @param webClientBuilder WebClient 빌더
     * @param kakaoApiKey 카카오 REST API 키
     */
    public KakaoAddressService(WebClient.Builder webClientBuilder,
                               @Value("${kakao.api.key}") String kakaoApiKey) {
        this.webClient = webClientBuilder.baseUrl("https://dapi.kakao.com").build();
        this.kakaoApiKey = kakaoApiKey;
    }

    /**
     * 입력된 주소를 위도/경도로 변환
     *
     * @param address 변환할 주소
     * @return [위도, 경도] 배열
     */
    public double[] getCoordinates(String address) {
        try {
            KakaoApiResponseDto response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v2/local/search/address.json")
                            .queryParam("query", address)
                            .build())
                    .header("Authorization", "KakaoAK " + kakaoApiKey)
                    .retrieve()
                    .bodyToMono(KakaoApiResponseDto.class)
                    .block();

            if (response != null && !response.getDocuments().isEmpty()) {
                // 변환 성공
                KakaoApiResponseDto.Document doc = response.getDocuments().get(0);
                double latitude = Double.parseDouble(doc.getY());  // 위도
                double longitude = Double.parseDouble(doc.getX()); // 경도
                log.info("🟢 카카오맵 주소 변환 성공: address={}, latitude={}, longitude={}", address, latitude, longitude);
                return new double[]{latitude, longitude};
            } else {
                // ❌ 변환 실패 (검색 결과 없음)
                log.error("🔴 카카오맵 주소 변환 실패 (검색 결과 없음): address={}", address);
                throw new RuntimeException("주소로 위도/경도를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            // ❌ API 호출 에러
            log.error("🔴 카카오맵 API 호출 실패: address={}, error={}", address, e.getMessage());
            throw new RuntimeException("카카오맵 API 호출 중 오류 발생");
        }
    }
}
