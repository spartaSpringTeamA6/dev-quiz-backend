package com.sparta.devquiz.domain.team.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
@Schema(description = "팀 관리자 변경 요청 dto")
public class TeamUpdateAdminRequest {

    @Column
    @Schema(description = "유저 닉네임", defaultValue = "도로롱")
    private String nickname;

}
