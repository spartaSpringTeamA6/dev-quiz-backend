package com.sparta.devquiz.domain.skill.service;

import com.sparta.devquiz.domain.skill.entity.Skill;
import com.sparta.devquiz.domain.skill.repository.SkillRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillService {

  private final SkillRepository skillRepository;

  public List<Skill> getMySkills(Long userId) {
    return skillRepository.findAllByUserId(userId);
  }
}
