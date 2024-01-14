package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.OauthType;
import com.sparta.devquiz.domain.user.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "유저 상세 정보 응답 dto")
public class UserDetailResponse {

  @Schema(description = "유저 id", defaultValue = "1")
  private Long userId;

  @Schema(description = "OAuth id", defaultValue = "1")
  private String oauthId;

  @Schema(description = "SNS", defaultValue = "GitHub")
  private OauthType oauthType;

  @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
  private String username;

  @Schema(description = "유저 역할", defaultValue = "USER")
  private UserRole role;

  @Schema(description = "총 코인", defaultValue = "100")
  private int totalCoin;

  @Schema(description = "총 스코어", defaultValue = "1000")
  private int weekScore;

  @Schema(description = "스킬", defaultValue = "자바")
  private List<SkillResponse> skillList;

  public static UserDetailResponse of(User user) {
    return UserDetailResponse
        .builder()
        .userId(user.getId())
        .oauthId(user.getOauthId())
        .oauthType(user.getOauthType())
        .username(user.getUsername())
        .role(user.getRole())
        .totalCoin(user.getTotalCoin())
        .weekScore(user.getWeekScore())
        .skillList(
            user.getSkillList().stream()
                .map(SkillResponse::of)
                .toList())
        .build();
  }

  public static List<UserDetailResponse> of(List<User> userList) {
    return userList.stream()
        .map(user -> UserDetailResponse.of(user))
        .toList();
  }

}