package com.sparta.devquiz.domain.quiz.repository;

import com.sparta.devquiz.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizUserRepository extends JpaRepository<Team, Long> {


}
