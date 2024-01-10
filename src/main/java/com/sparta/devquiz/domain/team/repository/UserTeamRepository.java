package com.sparta.devquiz.domain.team.repository;

import com.sparta.devquiz.domain.team.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {

    Boolean existsByUserIdAndTeamIdAndIsAcceptedTrue(Long userId, Long teamId);
}
