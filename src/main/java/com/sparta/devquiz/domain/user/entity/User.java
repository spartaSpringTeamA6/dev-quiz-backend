package com.sparta.devquiz.domain.user.entity;

import com.sparta.devquiz.domain.board.entity.Board;
import com.sparta.devquiz.domain.coin.entity.Coin;
import com.sparta.devquiz.domain.comment.entity.Comment;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.user.enums.OauthType;
import com.sparta.devquiz.domain.user.enums.UserRole;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
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

    @Column(nullable = false)
    private boolean isDeleted;

    @Column
    private LocalDateTime deletedAt;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skillList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Coin> coinList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<TeamUser> teamUserList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserQuiz> userQuizList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    public void updateUsernameAndSkill(String username, List<Skill> skillList) {
        this.username = username;
        this.skillList.clear();
        this.skillList.addAll(skillList);
    }
    public void deleteUser() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}