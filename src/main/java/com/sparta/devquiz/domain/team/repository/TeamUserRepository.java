package com.sparta.devquiz.domain.team.repository;

import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {

    Boolean existsByUserIdAndTeamIdAndIsAcceptedTrue(Long userId, Long teamId);

    Boolean existsByUserIdAndTeamIdAndIsAcceptedTrueAndUserRole(Long userId, Long teamId, TeamUserRole userRole);
}
