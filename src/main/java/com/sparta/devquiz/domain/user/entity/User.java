package com.sparta.devquiz.domain.user.entity;

import com.sparta.devquiz.domain.coin.entity.Coin;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.user.enums.OauthType;
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

    @Column
    @Enumerated(value = EnumType.STRING)
    private OauthType oauthType;

    @Column
    private String password;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private int totalCoin;

    @Column(nullable = false)
    private int weekScore;

    @Column
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Skill> skillList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Coin> coinList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserQuiz> userQuizList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<TeamUser> teamUserList = new ArrayList<>();
}