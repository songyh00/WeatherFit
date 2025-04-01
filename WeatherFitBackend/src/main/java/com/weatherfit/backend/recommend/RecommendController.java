package com.weatherfit.backend.recommend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

    private final OutfitService outfitService;

    public RecommendController(OutfitService outfitService) {
        this.outfitService = outfitService;
    }

    @GetMapping
    public List<String> recommend(
            @RequestParam int temperature,
            @RequestParam String gender,
            @RequestParam String style
    ) {
        return outfitService.recommendOutfits(gender, style, temperature);
    }
}