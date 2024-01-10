package com.sparta.devquiz.domain.team.repository;

import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {

    boolean existsByUserIdAndTeamIdAndIsAcceptedTrue(Long userId, Long teamId);

    boolean existsByUserIdAndTeamIdAndIsAcceptedTrueAndUserRole(Long userId, Long teamId, TeamUserRole userRole);

    Optional<TeamUser> findByTeamIdAndIsAcceptedTrueAndUserRole(Long teamId, TeamUserRole userRole);

    List<TeamUser> findAllByTeamIdAndIsAcceptedTrueAndUserRole(Long teamId, TeamUserRole userRole);
}
