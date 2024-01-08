package com.sparta.devquiz.domain.quiz.entity;

import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
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
public class UserQuiz extends BaseTimeEntity {

    @EmbeddedId
    private UserQuizId userQuizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    @MapsId("quizId")
    private Quiz quiz;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserQuizStatus status;

    @Column(nullable = false)
    private Long score;
}
