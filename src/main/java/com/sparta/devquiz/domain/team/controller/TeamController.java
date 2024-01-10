package com.sparta.devquiz.domain.team.controller;

import com.sparta.devquiz.domain.team.dto.request.TeamCreateRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamGetRequest;
import com.sparta.devquiz.domain.team.dto.response.TeamCreateResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamGetResponse;
import com.sparta.devquiz.domain.team.response.TeamResponseCode;
import com.sparta.devquiz.domain.team.service.TeamService;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.annotation.AuthUser;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
@Tag(name = "6. Team API", description = "Team 관련 API 입니다.")
public class TeamController {

    private final TeamService teamService;

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "TEAM-001",summary = "팀 생성")
    @PostMapping("")
    public ResponseEntity<CommonResponseDto> createTeam(
            @AuthUser User user,
            @RequestBody TeamCreateRequest request
    ) {

        TeamCreateResponse response = teamService.createTeam(user, request);

        return ResponseEntity.status(TeamResponseCode.CREATED_TEAM.getHttpStatus())
                .body(new CommonResponseDto(TeamResponseCode.CREATED_TEAM.getHttpStatus().value(),
                        TeamResponseCode.CREATED_TEAM.getMessage(), response));

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "TEAM-002",summary = "팀 상세조회")
    @GetMapping("/{team_id}")
    public ResponseEntity<CommonResponseDto> createTeam(
            @AuthUser User user,
            @PathVariable Long team_id,
            @RequestBody TeamGetRequest request
    ) {

        TeamGetResponse response = teamService.getTeam(user, team_id, request);

        return ResponseEntity.status(TeamResponseCode.OK_GET_TEAM_INFO.getHttpStatus())
                .body(new CommonResponseDto(TeamResponseCode.OK_GET_TEAM_INFO.getHttpStatus().value(),
                        TeamResponseCode.OK_GET_TEAM_INFO.getMessage(), response));

    }


}
