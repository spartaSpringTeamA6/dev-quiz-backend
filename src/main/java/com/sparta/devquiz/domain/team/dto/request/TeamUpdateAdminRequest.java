package com.sparta.devquiz.domain.team.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
@Schema(description = "팀 관리자 변경 요청 dto")
public class TeamUpdateAdminRequest {

    @Schema(description = "유저 이름", defaultValue = "도로롱")
    private String username;

}
