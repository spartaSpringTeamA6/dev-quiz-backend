package com.sparta.devquiz.domain.team.repository;

import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.entity.TeamUserId;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, TeamUserId> {

    Optional<TeamUser> findByTeamIdAndUserIdAndIsAcceptedTrue(Long teamId, Long userId);

    boolean existsByTeamIdAndUserIdAndIsAcceptedTrue(Long teamId, Long userId);

    boolean existsByTeamIdAndUserIdAndIsAcceptedTrueAndUserRole( Long teamId, Long userId, TeamUserRole userRole);

    Optional<TeamUser> findByTeamIdAndIsAcceptedTrueAndUserRole(Long teamId, TeamUserRole userRole);

    List<TeamUser> findAllByTeamIdAndIsAcceptedTrueAndUserRole(Long teamId, TeamUserRole userRole);
}
