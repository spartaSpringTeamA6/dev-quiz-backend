package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "유저 스킬 정보 응답 dto")
public class UserSkillResponse {
  @Schema(description = "유저 id", defaultValue = "1")
  private Long userId;

  @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
  private String username;

  @Schema(description = "유저 스킬", defaultValue = "JAVA")
  private List<SkillResponse> skillList;

  public static UserSkillResponse of(User user) {
    return UserSkillResponse
        .builder()
        .userId(user.getId())
        .username(user.getUsername())
        .skillList(user.getSkillList().stream()
            .map(SkillResponse::of)
            .toList())
        .build();
  }
}