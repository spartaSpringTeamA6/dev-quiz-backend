package com.sparta.devquiz.domain.team.repository;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.entity.TeamUserId;
import com.sparta.devquiz.domain.team.enums.TeamUserRole;
import com.sparta.devquiz.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, TeamUserId> {

    Optional<TeamUser> findByTeamIdAndUserId(Long teamId, Long userId);

    boolean existsByTeamIdAndUserIdAndIsAcceptedTrue(Long teamId, Long userId);

    boolean existsByTeamIdAndUserIdAndIsAcceptedTrueAndUserRole(Long teamId, Long userId, TeamUserRole userRole);

    Optional<TeamUser> findByTeamIdAndIsAcceptedTrueAndUserRole(Long teamId, TeamUserRole userRole);

    List<TeamUser> findAllByTeamIdAndIsAcceptedTrueAndUserRole(Long teamId, TeamUserRole userRole);

    List<TeamUser> findAllByUserIdAndIsAcceptedTrue(Long userId);

    List<TeamUser> findAllByUserIdAndIsAcceptedFalse(Long userId);

    Optional<TeamUser> findByTeamIdAndUserIdAndIsAcceptedFalse(Long teamId, Long userId);
}
