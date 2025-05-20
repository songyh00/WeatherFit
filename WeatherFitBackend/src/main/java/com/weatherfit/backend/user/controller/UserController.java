package com.weatherfit.backend.user.controller;

import com.weatherfit.backend.user.dto.UpdateProfileRequestDto;
import com.weatherfit.backend.user.dto.UserProfileResponseDto;
import com.weatherfit.backend.user.service.UserService;
import com.weatherfit.backend.auth.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관련 API를 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * 내 정보 조회
     */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponseDto> getMyProfile(HttpServletRequest request) {
        String token = jwtUtil.cleanTokenFromHeader(request.getHeader("Authorization"));
        return ResponseEntity.ok(userService.getMyProfile(token));
    }

    /**
     * 내 정보 수정 (이메일, 성별)
     */
    @PutMapping("/change-profile")
    public ResponseEntity<Void> updateProfile(@RequestBody @Valid UpdateProfileRequestDto requestDto,
                                              HttpServletRequest request) {
        String token = jwtUtil.cleanTokenFromHeader(request.getHeader("Authorization"));
        userService.updateProfile(token, requestDto);
        return ResponseEntity.ok().build();
    }
}
