package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.team.dto.response.TeamInfoResponse;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "유저 그룹 초대 정보 응답 dto")
public class UserInvitationsResponse {

  @Schema(description = "유저 id", defaultValue = "1")
  private Long userId;

  @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
  private String username;

  @Schema(description = "초대 팀 정보", defaultValue = "개발.zip")
  private List<TeamInfoResponse> teamInfoList;

  public static UserInvitationsResponse of(User user, List<TeamUser> teamUserList) {
    return UserInvitationsResponse
        .builder()
        .userId(user.getId())
        .username(user.getUsername())
        .teamInfoList(
            teamUserList.stream()
                .map(TeamUser::getTeam)
                .map(TeamInfoResponse::of)
                .toList())
        .build();
  }
  
}