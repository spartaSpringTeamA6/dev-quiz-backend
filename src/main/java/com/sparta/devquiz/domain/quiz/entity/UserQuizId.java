package com.sparta.devquiz.domain.quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Embeddable
@NoArgsConstructor
public class UserQuizId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quiz_id")
    private Long quizId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserQuizId userQuizId = (UserQuizId) o;
        return Objects.equals(getUserId(), userQuizId.getUserId()) && Objects.equals(getQuizId(), userQuizId.getQuizId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getQuizId());
    }
}