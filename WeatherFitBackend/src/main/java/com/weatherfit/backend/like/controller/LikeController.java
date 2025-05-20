package com.weatherfit.backend.like.controller;

import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.like.dto.LikeDto;
import com.weatherfit.backend.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 좋아요(Like) 기능을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final JwtUtil jwtUtil;

    /**
     * 좋아요 추가 또는 취소
     */
    @PostMapping("/{clothesId}")
    public String toggleLike(@RequestHeader("Authorization") String token,
                             @PathVariable Long clothesId) {

        Long userId = jwtUtil.extractUserId(token);
        likeService.toggleLike(userId, clothesId);
        return "좋아요 상태가 변경되었습니다.";
    }

    /**
     * 내가 좋아요한 옷 리스트 조회 (마이페이지용)
     */
    @GetMapping("/mypage")
    public List<LikeDto> getMyLikes(@RequestHeader("Authorization") String token) {

        Long userId = jwtUtil.extractUserId(token);
        return likeService.getMyLikes(userId);
    }

}