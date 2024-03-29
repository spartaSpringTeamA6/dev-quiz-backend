package com.sparta.devquiz.domain.team.dto.response;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.user.entity.User;
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
@Schema(description = "팀 조회 응답 dto")
public class TeamGetResponse {

    @Schema(description = "팀 id", defaultValue = "1")
    private Long id;

    @Schema(description = "팀 이름", defaultValue = "개발.zip")
    private String name;

    @Schema(description = "팀 관리자 닉네임", defaultValue = "용용선생")
    private String admin;

    @Builder.Default
    @Schema(description = "팀 유저 닉네임", defaultValue = "용용선생")
    private List<TeamUserResponse> userList = new ArrayList<>();

    public static TeamGetResponse of(Team team, TeamUser admin, List<TeamUser> userList) {
        return TeamGetResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .admin(admin.getUser().getUsername())
                .userList(userList.stream()
                        .map(i ->  TeamUserResponse.of(i.getUser()))
                        .collect(Collectors.toList()))
                .build();
    }

    @Builder
    @Getter
    public static class TeamUserResponse {
        private String username;
        private int totalCoin;

        public static TeamUserResponse of(User user) {
            return TeamUserResponse.builder()
                    .username(user.getUsername())
                    .totalCoin(user.getTotalCoin())
                    .build();
        }
    }
}

