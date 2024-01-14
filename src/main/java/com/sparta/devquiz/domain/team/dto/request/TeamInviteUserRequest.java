package com.sparta.devquiz.domain.team.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "팀 유저 초대 요청 dto")
public class TeamInviteUserRequest {

    @Column
    List<String> user = new LinkedList<>();
}
