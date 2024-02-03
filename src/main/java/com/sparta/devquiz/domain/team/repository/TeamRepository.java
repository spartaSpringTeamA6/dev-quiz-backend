package com.sparta.devquiz.domain.team.repository;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.exception.TeamCustomException;
import com.sparta.devquiz.domain.team.exception.TeamExceptionCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByIdAndIsDeletedFalse(Long teamId);

    boolean existsByName(String name);

    default Team findTeamByIdOrElseThrow(Long teamId) {
        return findByIdAndIsDeletedFalse(teamId).orElseThrow(
                () -> new TeamCustomException(TeamExceptionCode.NOT_FOUND_TEAM)
        );
    }

    default boolean existsTeamByName(String name){
        return existsByName(name);
    }

}
