package com.weatherfit.backend.auth.controller;

import com.weatherfit.backend.auth.dto.*;
import com.weatherfit.backend.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 관련 API를 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDto requestDto) {
        authService.signup(requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 아이디 중복확인
     */
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> isUsernameTaken(@RequestParam String username) {
        return ResponseEntity.ok(authService.isUsernameTaken(username));
    }

    /**
     * 이메일 중복확인
     */
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> isEmailTaken(@RequestParam String email) {
        return ResponseEntity.ok(authService.isEmailTaken(email));
    }

    /**
     * 아이디 찾기
     */
    @PostMapping("/find-username")
    public ResponseEntity<String> findUsername(@RequestBody FindUsernameRequestDto requestDto) {
        return ResponseEntity.ok(authService.findUsername(requestDto.getEmail(), requestDto.getPassword()));
    }

    /**
     * 비밀번호 재설정 검증(로그인 전)
     */
    @PostMapping("/verify-reset-password")
    public ResponseEntity<Void> verifyResetPassword(@RequestBody PasswordResetVerificationRequestDto requestDto) {
        authService.verifyUser(requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 비밀번호 재설정(로그인 전)
     */
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetRequestDto requestDto) {
        authService.resetPassword(requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    /**
     * 비밀번호 변경(로그인 후)
     */
    @PostMapping("/change-password")
    public ResponseEntity<Void> changeMyPassword(@RequestBody PasswordChangeRequestDto requestDto,
                                                 HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        authService.changeMyPassword(token, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        authService.withdraw(token);
        return ResponseEntity.ok().build();
    }
}