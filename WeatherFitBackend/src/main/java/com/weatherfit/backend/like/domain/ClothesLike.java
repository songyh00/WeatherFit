package com.weatherfit.backend.like.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ClothesLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "users_id", nullable = false)
    private Long usersId;

    @Column(name = "clothes_id", nullable = false)
    private Long clothesId;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false,
            columnDefinition = "timestamp default current_timestamp")
    private java.time.LocalDateTime createdAt;

    public ClothesLike(Long usersId, Long clothesId) {
        this.usersId = usersId;
        this.clothesId = clothesId;
    }
}
