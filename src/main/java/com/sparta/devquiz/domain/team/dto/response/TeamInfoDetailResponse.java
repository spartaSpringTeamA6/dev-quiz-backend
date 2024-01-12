package com.sparta.devquiz.domain.team.dto.response;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.user.dto.response.UserInfoResponse;
import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "팀 정보 디테일 응답 dto")
public class TeamInfoDetailResponse {

    @Column
    @Schema(description = "팀 id", defaultValue = "1")
    private Long teamId;

    @Column
    @Schema(description = "팀 이름", defaultValue = "개발.zip")
    private String name;

    @Column
    @Schema(description = "팀 관리자", defaultValue = "개발.zip")
    private UserInfoResponse admin;

    @Column
    @Schema(description = "팀 관리자", defaultValue = "개발.zip")
    private List<UserInfoResponse> userList = new ArrayList<>();

    public static TeamInfoDetailResponse of(Team team, User admin, List<User> userList) {
        return TeamInfoDetailResponse.builder()
                .teamId(team.getId())
                .name(team.getName())
                .admin(UserInfoResponse.of(admin))
                .userList(UserInfoResponse.of(userList))
                .build();
    }
}
