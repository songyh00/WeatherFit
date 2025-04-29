package com.weatherfit.backend.user.controller;

import com.weatherfit.backend.auth.JwtUtil;
import com.weatherfit.backend.user.dto.UpdateProfileRequestDto;
import com.weatherfit.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자(User) 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * 사용자 프로필 수정 (이메일, 성별)
     */
    @PutMapping("/update")
    public String updateProfile(@RequestHeader("Authorization") String token,
                                @RequestBody UpdateProfileRequestDto requestDto) {

        Long userId = jwtUtil.extractUserId(token);
        userService.updateProfile(userId, requestDto.getNewEmail(), requestDto.getNewGender());
        return "프로필 수정이 완료되었습니다.";
    }

}