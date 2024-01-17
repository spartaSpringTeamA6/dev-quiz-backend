package com.sparta.devquiz.domain.quiz.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Embeddable
public class UserQuizId implements Serializable {

    private Long userId;
    private Long quizId;
    public UserQuizId(Long userId, Long quizId) {
        this.userId = userId;
        this.quizId = quizId;
    }

    public UserQuizId() {
    }
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