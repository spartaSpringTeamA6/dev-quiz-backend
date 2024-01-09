package com.sparta.devquiz.domain.team.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;

@Getter
@Embeddable
public class UserTeamId implements Serializable {

    private Long userId;
    private Long teamId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserTeamId userTeamId = (UserTeamId) o;
        return Objects.equals(getUserId(), userTeamId.getUserId()) && Objects.equals(
            getTeamId(), userTeamId.getTeamId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTeamId());
    }
}
