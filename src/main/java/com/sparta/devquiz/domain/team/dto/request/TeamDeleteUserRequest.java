package com.sparta.devquiz.domain.team.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
@Schema(description = "팀 멤버 추방 요청 dto")
public class TeamDeleteUserRequest {

    @Schema(description = "유저 이름", defaultValue = "롬롬롬")
    private String username;

}