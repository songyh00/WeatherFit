package com.weatherfit.backend.like.controller;

import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.like.dto.LikeDto;
import com.weatherfit.backend.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 좋아요(Like) 기능을 처리하는 컨트롤러
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
     *
     * @param token Authorization 헤더의 JWT 토큰
     * @param clothesId 좋아요할 옷 ID
     * @return 좋아요 상태 변경 결과 메시지
     */
    @PostMapping("/{clothesId}")
    public String toggleLike(@RequestHeader("Authorization") String token,
                             @PathVariable Long clothesId) {

        // "Bearer " 접두어 제거 후 실제 토큰 추출
        String tokenValue = token.replace("Bearer ", "");

        // 토큰에서 사용자 ID 추출
        Long userId = jwtUtil.extractUserId(tokenValue);

        // 좋아요 토글
        likeService.toggleLike(userId, clothesId);

        return "좋아요 상태가 변경되었습니다.";
    }

    /**
     * 내가 좋아요한 옷 리스트 조회 (마이페이지용)
     *
     * @param token Authorization 헤더의 JWT 토큰
     * @return 내가 좋아요한 옷 리스트
     */
    @GetMapping("/mypage")
    public List<LikeDto> getMyLikes(@RequestHeader("Authorization") String token) {

        // "Bearer " 접두어 제거 후 실제 토큰 추출
        String tokenValue = token.replace("Bearer ", "");

        // 토큰에서 사용자 ID 추출
        Long userId = jwtUtil.extractUserId(tokenValue);

        // 사용자가 좋아요한 옷 리스트 반환
        return likeService.getMyLikes(userId);
    }
}
