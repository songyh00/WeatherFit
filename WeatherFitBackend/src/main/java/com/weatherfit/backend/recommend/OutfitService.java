package com.weatherfit.backend.recommend;

import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class OutfitService {

    private final OutfitRepository outfitRepository;

    public OutfitService(OutfitRepository outfitRepository) {
        this.outfitRepository = outfitRepository;
    }

    public List<String> recommendOutfits(String gender, String style, int temperature) {
        List<String> genderOptions = gender.equals("FEMALE") ? List.of("FEMALE", "UNISEX") : List.of("MALE", "UNISEX");

        List<Outfit> candidates = outfitRepository
                .findByGenderInAndStyleAndMinTemperatureLessThanEqualAndMaxTemperatureGreaterThanEqual(
                        genderOptions, style, temperature, temperature
                );

        Collections.shuffle(candidates);

        return candidates.stream()
                .limit(3)
                .map(Outfit::getImageUrl)
                .toList();
    }
}
