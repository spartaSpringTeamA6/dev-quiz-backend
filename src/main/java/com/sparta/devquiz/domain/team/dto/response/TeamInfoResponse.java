package com.sparta.devquiz.domain.team.dto.response;

import com.sparta.devquiz.domain.team.entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "팀 정보 응답 dto")
public class TeamInfoResponse {

    @Column
    @Schema(description = "팀 id", defaultValue = "1")
    private Long teamId;

    @Column
    @Schema(description = "팀 이름", defaultValue = "개발.zip")
    private String name;

    public static TeamInfoResponse of(Team team) {
        return TeamInfoResponse.builder()
                .teamId(team.getId())
                .name(team.getName())
                .build();
    }
}
