package com.sparta.devquiz.domain.team.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "팀 생성 요청 dto")
public class TeamCreateRequest {

    @Schema(description = "팀 이름", defaultValue = "개발.zip")
    @NotBlank(message = "팀 이름을 입력하세요.")
    @Size(max = 50, message = "팀 이름을 50자 이하로 입력하세요.")
    private String name;
}