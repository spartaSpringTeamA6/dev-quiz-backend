package com.sparta.devquiz.domain.skill.repository;

import com.sparta.devquiz.domain.skill.entity.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillRepository extends JpaRepository<Skill, Long> {

  @Query("select s from Skill s where s.user.id = :userId")
  List<Skill> findAllByUserId(Long userId);
}