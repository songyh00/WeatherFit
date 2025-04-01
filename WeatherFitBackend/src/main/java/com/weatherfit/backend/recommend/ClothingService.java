package com.weatherfit.backend.recommend;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClothingService {

    private final ClothingRepository clothingRepository;

    public ClothingService(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    public Map<String, List<String>> recommendOutfits(int temperature, Clothing.WeatherType weatherType, Clothing.Gender userGender) {
        Map<String, List<String>> result = new HashMap<>();

        for (Clothing.Category category : Clothing.Category.values()) {
            // 더운 날씨에 아우터 제외 or light 스타일만
            if (category == Clothing.Category.OUTER && temperature >= 23) {
                List<Clothing> candidates = clothingRepository
                        .findByCategoryAndMinTemperatureLessThanEqualAndMaxTemperatureGreaterThanEqualAndWeatherTypeIn(
                                category,
                                temperature,
                                temperature,
                                List.of(weatherType, Clothing.WeatherType.ANY)
                        );

                // light 스타일 필터링
                candidates.removeIf(clothing ->
                        clothing.getStyle() == null || !clothing.getStyle().equalsIgnoreCase("light"));

                // 성별 필터링
                candidates.removeIf(clothing ->
                        clothing.getGender() != Clothing.Gender.UNISEX && clothing.getGender() != userGender);

                result.put("outer", pickRandomImages(candidates, 4));
                continue;
            }

            // 나머지 카테고리 (상의, 하의)
            List<Clothing> candidates = clothingRepository
                    .findByCategoryAndMinTemperatureLessThanEqualAndMaxTemperatureGreaterThanEqualAndWeatherTypeIn(
                            category,
                            temperature,
                            temperature,
                            List.of(weatherType, Clothing.WeatherType.ANY)
                    );

            // 성별 필터링
            candidates.removeIf(clothing ->
                    clothing.getGender() != Clothing.Gender.UNISEX && clothing.getGender() != userGender);

            result.put(category.name().toLowerCase(), pickRandomImages(candidates, 4));
        }

        return result;
    }

    private List<String> pickRandomImages(List<Clothing> candidates, int limit) {
        Collections.shuffle(candidates);
        return candidates.stream()
                .limit(limit)
                .map(Clothing::getImageUrl)
                .toList();
    }
}
