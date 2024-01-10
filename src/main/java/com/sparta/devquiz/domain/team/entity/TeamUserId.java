package com.sparta.devquiz.domain.team.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;

@Getter
@Embeddable
public class TeamUserId implements Serializable {

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
        TeamUserId teamUserId = (TeamUserId) o;
        return Objects.equals(getUserId(), teamUserId.getUserId()) && Objects.equals(
            getTeamId(), teamUserId.getTeamId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTeamId());
    }
}
