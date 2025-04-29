package com.weatherfit.backend.weather.service;

import com.weatherfit.backend.common.exception.CustomException;
import com.weatherfit.backend.common.exception.ErrorCode;
import com.weatherfit.backend.weather.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 카카오맵 API를 이용해 주소를 위도/경도로 변환하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressService {

    private final WebClient webClient;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public double[] getCoordinates(String address) {
        try {
            KakaoApiResponseDto response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("dapi.kakao.com")
                            .path("/v2/local/search/address.json")
                            .queryParam("query", address)
                            .build())
                    .header("Authorization", "KakaoAK " + kakaoApiKey)
                    .retrieve()
                    .bodyToMono(KakaoApiResponseDto.class)
                    .block();

            if (response != null && !response.getDocuments().isEmpty()) {
                var doc = response.getDocuments().get(0);
                double lat = Double.parseDouble(doc.getY());
                double lng = Double.parseDouble(doc.getX());
                log.info("🟢 주소 변환 성공: {}, {}, {}", address, lat, lng);
                return new double[]{lat, lng};
            } else {
                log.warn("🔴 주소 변환 실패 (검색 결과 없음): {}", address);
                throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
            }
        } catch (Exception e) {
            log.error("🔴 카카오 API 오류: {}, {}", address, e.getMessage());
            throw new CustomException(ErrorCode.EXTERNAL_API_ERROR);
        }
    }
}
