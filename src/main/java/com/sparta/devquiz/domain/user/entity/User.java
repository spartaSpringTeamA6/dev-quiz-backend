package com.sparta.devquiz.domain.user.entity;

import com.sparta.devquiz.domain.coin.entity.Coin;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.user.enums.UserRole;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String oauthId;

    @Column(nullable = false, length = 10, unique = true)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "user")
    private List<Skill> skillList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Coin> coinList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserQuiz> userQuizList = new ArrayList<>();
}