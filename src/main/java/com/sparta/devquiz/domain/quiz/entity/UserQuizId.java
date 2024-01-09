package com.sparta.devquiz.domain.quiz.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;


@Getter
@Embeddable
public class UserQuizId implements Serializable {

    private Long userId;
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