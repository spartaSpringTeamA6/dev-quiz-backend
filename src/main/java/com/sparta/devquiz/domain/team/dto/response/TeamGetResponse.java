package com.sparta.devquiz.domain.team.dto.response;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "팀 조회 응답 dto")
public class TeamGetResponse {

    @Column
    @Schema(description = "팀 이름", defaultValue = "개발.zip")
    private String name;

    @Column
    @Schema(description = "팀 관리자 닉네임", defaultValue = "용용선생")
    private String admin;

    @Column
    @Schema(description = "팀 유저 닉네임", defaultValue = "용용선생")
    private List<String> userList = new ArrayList<>();

    public static TeamGetResponse of(Team team, TeamUser admin, List<TeamUser> userList) {
        return TeamGetResponse.builder()
                .name(team.getName())
                .admin(admin.getUser().getNickname())
                .userList(userList.stream()
                        .map(i -> i.getUser().getNickname())
                        .collect(Collectors.toList()))
                .build();
    }
}

