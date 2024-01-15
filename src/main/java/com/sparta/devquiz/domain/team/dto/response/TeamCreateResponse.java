package com.sparta.devquiz.domain.team.dto.response;

import com.sparta.devquiz.domain.team.entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Schema(description = "팀 생성 응답 dto")
public class TeamCreateResponse {

    @Schema(description = "팀 이름", defaultValue = "개발.zip")
    private String name;

    public static TeamCreateResponse of(Team team) {
        return TeamCreateResponse.builder()
                .name(team.getName())
                .build();
    }

}
