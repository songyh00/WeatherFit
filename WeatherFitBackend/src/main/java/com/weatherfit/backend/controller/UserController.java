package com.weatherfit.backend.controller;

import com.weatherfit.backend.dto.LoginRequest;
import com.weatherfit.backend.entity.User;
import com.weatherfit.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // React 포트
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(Map.of("success", true));
            }
        }

        return ResponseEntity.ok(Map.of("success", false));
    }

    // ✨ 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User joinRequest) {
        if (userRepository.findByUsername(joinRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "이미 사용 중인 사용자명입니다."));
        }

        if (userRepository.findByEmail(joinRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "이미 사용 중인 이메일입니다."));
        }

        userRepository.save(joinRequest);

        return ResponseEntity.ok(Map.of("success", true, "message", "회원가입이 완료되었습니다."));
    }

    // ✨ 사용자명 중복 검사
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        boolean exists = userRepository.findByUsername(username).isPresent();
        return ResponseEntity.ok(Map.of("available", !exists));
    }

    // ✨ 이메일 중복 검사
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = userRepository.findByEmail(email).isPresent();
        return ResponseEntity.ok(Map.of("available", !exists));
    }
}
