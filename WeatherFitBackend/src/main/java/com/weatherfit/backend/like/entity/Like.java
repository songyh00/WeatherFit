package com.weatherfit.backend.like.entity;

import com.weatherfit.backend.clothes.entity.Clothes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "clothes_like", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"users_id", "clothes_id"})
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "users_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_id", nullable = false)
    private Clothes clothes;

    public Like(Long userId, Clothes clothes) {
        this.userId = userId;
        this.clothes = clothes;
    }
}
