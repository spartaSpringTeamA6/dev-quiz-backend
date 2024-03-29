package com.sparta.devquiz.domain.skill.entity;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.skill.enums.UserSkill;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skill extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private UserSkill userSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Skill(UserSkill userSkill, User user) {
        this.userSkill = userSkill;
        this.user = user;
    }
}
