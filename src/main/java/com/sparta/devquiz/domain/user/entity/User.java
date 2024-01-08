package com.sparta.devquiz.domain.user.entity;

import com.sparta.devquiz.domain.point.entity.Point;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.score.entity.Score;
import com.sparta.devquiz.domain.user.enums.UserRole;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "user")
    private List<Skill> skillList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Score> scoreList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Point> pointList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserQuiz> userQuizList = new ArrayList<>();

}
