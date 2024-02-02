package com.sparta.devquiz.domain.team.repository;

import com.sparta.devquiz.domain.team.entity.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByIdAndIsDeletedFalse(Long teamId);

    boolean existsByName(String Name);
}
