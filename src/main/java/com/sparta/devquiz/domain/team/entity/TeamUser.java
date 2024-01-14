package com.sparta.devquiz.domain.team.entity;

import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.UserRole;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamUser extends BaseTimeEntity {

    @EmbeddedId
    private TeamUserId teamUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @MapsId("teamId")
    private Team team;

    @Column
    @Enumerated(value = EnumType.STRING)
    private TeamUserRole userRole;

    @Column(nullable = false)
    private boolean isAccepted;

    @Column
    private LocalDateTime acceptedAt;

    @Builder
    private TeamUser(Team team, User user, TeamUserRole userRole, boolean isAccepted){
        this.teamUserId = TeamUserId.builder()
                .teamId(team.getId())
                .userId(user.getId())
                .build();
        this.team = team;
        this.user = user;
        this.userRole = userRole;
        this.isAccepted = isAccepted;
    }

    public void updateTeamUserRole(TeamUserRole teamUserRole){
        this.userRole = teamUserRole;
    }

    public void acceptInvitation() {
        this.isAccepted = true;
    }
}