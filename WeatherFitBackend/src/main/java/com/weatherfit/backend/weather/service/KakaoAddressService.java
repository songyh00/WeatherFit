package com.weatherfit.backend.weather.service;

import com.weatherfit.backend.weather.dto.KakaoApiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoAddressService {

    private final WebClient webClient;
    private final String kakaoApiKey;

    public KakaoAddressService(WebClient.Builder webClientBuilder,
                               @Value("${kakao.api.key}") String kakaoApiKey) {
        this.webClient = webClientBuilder.baseUrl("https://dapi.kakao.com").build();
        this.kakaoApiKey = kakaoApiKey;
    }

    public double[] getCoordinates(String address) {
        // 카카오맵 주소 검색 API 호출
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
            KakaoApiResponseDto.Document doc = response.getDocuments().get(0);
            double latitude = Double.parseDouble(doc.getY());
            double longitude = Double.parseDouble(doc.getX());
            return new double[]{latitude, longitude};
        } else {
            throw new RuntimeException("주소로 위도/경도를 찾을 수 없습니다.");
        }
    }
}
