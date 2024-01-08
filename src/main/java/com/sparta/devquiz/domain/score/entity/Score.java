package com.sparta.devquiz.domain.score.entity;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scores")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Score extends BaseTimeEntity {

    @Id
    @Column(name = "score_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
