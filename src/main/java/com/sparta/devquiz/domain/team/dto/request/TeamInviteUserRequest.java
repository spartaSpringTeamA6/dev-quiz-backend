package com.sparta.devquiz.domain.team.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "팀 유저 초대 요청 dto")
public class TeamInviteUserRequest {

    @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(max = 30, message = "유저명을 30자 이하로 입력하세요.")
    private String username;
}