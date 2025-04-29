package com.weatherfit.backend.user.entity;

import com.weatherfit.backend.common.enumtype.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자(User) 엔티티
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
