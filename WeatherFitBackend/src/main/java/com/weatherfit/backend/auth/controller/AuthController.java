package com.weatherfit.backend.auth.controller;

import com.weatherfit.backend.auth.service.AuthService;
import com.weatherfit.backend.auth.dto.LoginRequestDto;
import com.weatherfit.backend.auth.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 인증(Auth) 관련 요청을 처리하는 Controller
 * - 로그인, 회원가입, 아이디/비번 찾기, 회원 탈퇴 기능 제공
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인 요청
     * @param requestDto 로그인 요청 정보 (username, password)
     * @return 토큰 + username 반환
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDto requestDto) {
        String token = authService.login(requestDto);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", requestDto.getUsername());
        return response;
    }

    /**
     * 회원가입 요청
     * @param requestDto 회원가입 요청 정보 (username, password, email, gender)
     * @return 성공 메시지
     */
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto requestDto) {
        authService.signup(requestDto);
        return "회원가입이 완료되었습니다.";
    }

    /**
     * 아이디 중복확인 요청
     * @param username 확인할 사용자명
     * @return 사용 가능 여부
     */
    @GetMapping("/check-username")
    public String checkUsername(@RequestParam String username) {
        boolean exists = authService.isUsernameTaken(username);
        if (exists) {
            throw new RuntimeException("이미 사용중인 아이디입니다.");
        }
        return "사용 가능한 아이디입니다.";
    }

    /**
     * 이메일 중복확인 요청
     * @param email 확인할 이메일
     * @return 사용 가능 여부
     */
    @GetMapping("/check-email")
    public String checkEmail(@RequestParam String email) {
        boolean exists = authService.isEmailTaken(email);
        if (exists) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }
        return "사용 가능한 이메일입니다.";
    }

    /**
     * 아이디 찾기 요청
     * @param email 사용자의 이메일
     * @param password 사용자의 비밀번호
     * @return username 반환
     */
    @PostMapping("/find-username")
    public String findUsername(@RequestParam String email,
                               @RequestParam String password) {
        return authService.findUsername(email, password);
    }

    /**
     * 비밀번호 찾기 요청
     * @param username 사용자의 아이디
     * @param email 사용자의 이메일
     * @return password 반환
     */
    @PostMapping("/find-password")
    public String findPassword(@RequestParam String username,
                               @RequestParam String email) {
        return authService.findPassword(username, email);
    }

    /**
     * 회원 탈퇴 요청
     * @param token Authorization 헤더에 담긴 토큰 ("Bearer {token}" 형태)
     * @return 성공 메시지
     */
    @DeleteMapping("/withdraw")
    public String withdraw(@RequestHeader("Authorization") String token) {
        // "Bearer " 접두어 제거 후 실제 토큰만 추출
        String tokenValue = token.startsWith("Bearer ") ? token.substring(7) : token;
        authService.withdraw(tokenValue);
        return "회원 탈퇴가 완료되었습니다.";
    }
}
