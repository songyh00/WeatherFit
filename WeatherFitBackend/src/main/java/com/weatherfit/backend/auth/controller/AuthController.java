package com.weatherfit.backend.auth.controller;

import com.weatherfit.backend.auth.dto.ChangePasswordRequestDto;
import com.weatherfit.backend.auth.dto.LoginRequestDto;
import com.weatherfit.backend.auth.dto.LoginResponseDto;
import com.weatherfit.backend.auth.dto.SignupRequestDto;
import com.weatherfit.backend.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 회원(Auth) 관련 요청을 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequestDto requestDto) {
        authService.signup(requestDto);
    }

    /**
     * 아이디 중복 확인
     */
    @GetMapping("/check-username")
    public boolean checkUsername(@RequestParam String username) {
        return authService.isUsernameTaken(username);
    }

    /**
     * 이메일 중복 확인
     */
    @GetMapping("/check-email")
    public boolean checkEmail(@RequestParam String email) {
        return authService.isEmailTaken(email);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
        return authService.login(requestDto);
    }

    /**
     * 아이디 찾기
     */
    @PostMapping("/find-username")
    public String findUsername(@RequestParam String email,
                               @RequestParam String password) {
        return authService.findUsername(email, password);
    }

    /**
     * 비밀번호 재설정 검증
     */
    @PostMapping("/verify-user")
    public void verifyUser(@RequestParam String username,
                           @RequestParam String email) {
        authService.verifyUser(username, email);
    }

    /**
     * 비밀번호 재설정
     */
    @PostMapping("/change-password")
    public void changePassword(@RequestParam String username,
                               @RequestParam String newPassword) {
        authService.changePassword(username, newPassword);
    }

    /**
     * 로그인 후 비밀번호 변경
     */
    @PostMapping("/change-my-password")
    public void changeMyPassword(@RequestHeader("Authorization") String token,
                                 @RequestBody ChangePasswordRequestDto requestDto) {
        authService.changeMyPassword(token, requestDto);
    }
    
    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/withdraw")
    public void withdraw(@RequestHeader("Authorization") String token) {
        authService.withdraw(token);
    }

}