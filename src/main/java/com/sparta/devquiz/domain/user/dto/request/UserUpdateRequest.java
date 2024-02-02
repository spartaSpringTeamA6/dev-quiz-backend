package com.sparta.devquiz.domain.user.dto.request;

import com.sparta.devquiz.domain.user.annotation.UserSkillEnum;
import com.sparta.devquiz.domain.user.enums.UserSkill;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "유저 프로필 수정 요청 dto")
public class UserUpdateRequest {

  @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
  @NotBlank(message = "닉네임을 입력하세요.")
  @Size(max = 30, message = "유저네임을 30자 이하로 입력하세요.")
  private String username;

  @Schema(description = "스킬")
  @UserSkillEnum(enumClass = UserSkill.class, ignoreCase = true)
  private List<String> skillList;
}