package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.skill.entity.Skill;
import com.sparta.devquiz.domain.user.enums.UserSkill;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "스킬 정보 응답 dto")
public class SkillResponse {

  @Schema(description = "유저 스킬", defaultValue = "JAVA")
  private UserSkill userSkill;

  public static SkillResponse of(Skill skill) {
    return SkillResponse
        .builder()
        .userSkill(skill.getUserSkill())
        .build();
  }

  public static List<SkillResponse> of(List<Skill> skillList) {
    return skillList.stream()
        .map(SkillResponse::of)
        .toList();
  }
}