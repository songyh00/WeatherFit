package com.weatherfit.backend.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자(User) 엔티티
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 사용자 고유 ID (자동 증가)

    @Column(nullable = false, unique = true)
    private String username;  // 사용자 이름 (로그인 ID, 유니크)

    @Column(nullable = false)
    private String password;  // 비밀번호 (현재는 암호화 안함)

    @Column(nullable = false, unique = true)
    private String email;     // 사용자 이메일 (유니크)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;    // 성별 (MALE / FEMALE)

    @Column(name = "created_at", updatable = false)
    private java.sql.Timestamp createdAt;  // 가입일 (추후 설정 가능)

    /**
     * 성별 ENUM 타입
     */
    public enum Gender {
        MALE, FEMALE
    }
}
