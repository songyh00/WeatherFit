package com.weatherfit.backend.like.entity;

import com.weatherfit.backend.clothes.entity.Clothes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 좋아요(Like) 엔티티
 * 사용자가 옷(Clothes)에 좋아요를 누른 기록을 저장
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "clothes_like", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"users_id", "clothes_id"}) // 사용자 + 옷 조합은 유니크해야 함
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 좋아요 고유 ID

    @Column(name = "users_id", nullable = false)
    private Long userId; // 좋아요 누른 사용자 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_id", nullable = false)
    private Clothes clothes; // 좋아요 누른 옷 (Clothes 엔티티와 연관)

    /**
     * 좋아요 생성자
     * @param userId 사용자 ID
     * @param clothes 좋아요한 옷
     */
    public Like(Long userId, Clothes clothes) {
        this.userId = userId;
        this.clothes = clothes;
    }
}
