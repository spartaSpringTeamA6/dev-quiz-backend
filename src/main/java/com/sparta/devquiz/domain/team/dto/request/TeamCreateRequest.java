package com.sparta.devquiz.domain.team.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
@Schema(description = "팀 생성 요청 dto")
public class TeamCreateRequest {

    @Schema(description = "팀 이름", defaultValue = "개발.zip")
    private String name;

}
