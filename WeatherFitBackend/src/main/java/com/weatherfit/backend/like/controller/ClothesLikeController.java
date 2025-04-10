package com.weatherfit.backend.like.controller;

import com.weatherfit.backend.like.service.ClothesLikeService;
import com.weatherfit.backend.recommend.dto.ClothesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class ClothesLikeController {

    private final ClothesLikeService clothesLikeService;

    @PostMapping("/{clothesId}")
    public String toggleLike(@RequestParam Long usersId, @PathVariable Long clothesId) {
        boolean liked = clothesLikeService.toggleLike(usersId, clothesId);
        return liked ? "좋아요 추가" : "좋아요 취소";
    }

    @GetMapping("/my")
    public List<ClothesDTO> getMyLikedClothes(@RequestParam Long usersId) {
        return clothesLikeService.getUserLikedClothes(usersId);
    }
}
