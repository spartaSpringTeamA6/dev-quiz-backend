package com.sparta.devquiz.domain.user.dto.request;

import com.sparta.devquiz.domain.user.annotation.UserSkillEnum;
import com.sparta.devquiz.domain.skill.enums.UserSkill;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "유저 프로필 수정 요청 dto")
public class UserSkillsUpdateRequest {

  @Schema(description = "스킬")
  @UserSkillEnum(enumClass = UserSkill.class, ignoreCase = true)
  private List<String> skillList;
}