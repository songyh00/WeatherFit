package com.weatherfit.backend.like.controller;

import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.like.dto.LikeDto;
import com.weatherfit.backend.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 좋아요(Like) 기능 컨트롤러
 * - 좋아요 추가/취소
 * - 마이페이지: 내가 좋아요한 옷 조회
 */
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final JwtUtil jwtUtil;

    /**
     * 옷 좋아요 추가 또는 취소
     */
    @PostMapping("/{clothesId}")
    public String toggleLike(@RequestHeader("Authorization") String token,
                             @PathVariable Long clothesId) {
        String tokenValue = token.replace("Bearer ", "");
        Long userId = jwtUtil.extractUserId(tokenValue);

        likeService.toggleLike(userId, clothesId);
        return "좋아요 상태가 변경되었습니다.";
    }

    /**
     * 내가 좋아요한 옷 리스트 조회 (마이페이지)
     */
    @GetMapping("/mypage")
    public List<LikeDto> getMyLikes(@RequestHeader("Authorization") String token) {
        String tokenValue = token.replace("Bearer ", "");
        Long userId = jwtUtil.extractUserId(tokenValue);

        return likeService.getMyLikes(userId);
    }
}
