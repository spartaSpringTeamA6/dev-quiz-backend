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

    Optional<TeamUser> findByTeamAndUser(Team team, User user);

    boolean existsByTeamAndUserAndIsAcceptedTrue(Team team, User user);

    boolean existsByTeamAndUserAndIsAcceptedTrueAndUserRole(Team team, User user, TeamUserRole userRole);

    Optional<TeamUser> findByTeamAndIsAcceptedTrueAndUserRole(Team team, TeamUserRole userRole);

    List<TeamUser> findAllByTeamAndIsAcceptedTrueAndUserRole(Team team, TeamUserRole userRole);

    List<TeamUser> findAllByUserAndIsAcceptedTrue(User user);

    List<TeamUser> findAllByUserAndIsAcceptedFalse(User user);

    Optional<TeamUser> findByTeamAndUserAndIsAcceptedFalse(Team team, User user);
}
