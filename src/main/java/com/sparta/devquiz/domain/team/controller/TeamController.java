package com.sparta.devquiz.domain.team.controller;

import com.sparta.devquiz.domain.team.dto.request.TeamCreateRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamDeleteUserRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamGetRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamUpdateAdminRequest;
import com.sparta.devquiz.domain.team.dto.request.TeamUpdateNameRequest;
import com.sparta.devquiz.domain.team.dto.response.TeamCreateResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamDeleteUserResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamGetResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamUpdateAdminResponse;
import com.sparta.devquiz.domain.team.dto.response.TeamUpdateNameResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<CommonResponseDto> createTeam(@AuthUser User user,
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
    public ResponseEntity<CommonResponseDto> getTeam(@AuthUser User user, @PathVariable Long team_id,
            @RequestBody TeamGetRequest request
    ) {

        TeamGetResponse response = teamService.getTeam(user, team_id, request);

        return ResponseEntity.status(TeamResponseCode.OK_GET_TEAM_INFO.getHttpStatus())
                .body(new CommonResponseDto(TeamResponseCode.OK_GET_TEAM_INFO.getHttpStatus().value(),
                        TeamResponseCode.OK_GET_TEAM_INFO.getMessage(), response));

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "TEAM-003",summary = "팀 이름 수정")
    @PatchMapping("/{team_id}/name")
    public ResponseEntity<CommonResponseDto> updateTeamName(@AuthUser User user, @PathVariable Long team_id,
            @RequestBody TeamUpdateNameRequest request
    ) {

        TeamUpdateNameResponse response = teamService.updateTeamName(user, team_id, request);

        return ResponseEntity.status(TeamResponseCode.NO_CONTENT_UPDATE_TEAM_NAME.getHttpStatus())
                .body(new CommonResponseDto(TeamResponseCode.NO_CONTENT_UPDATE_TEAM_NAME.getHttpStatus().value(),
                        TeamResponseCode.NO_CONTENT_UPDATE_TEAM_NAME.getMessage(), response));

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "TEAM-004",summary = "팀 관리자 변경")
    @PatchMapping("/{team_id}/admin")
    public ResponseEntity<CommonResponseDto> updateTeamAdmin(@AuthUser User user, @PathVariable Long team_id,
            @RequestBody TeamUpdateAdminRequest request
    ) {

        TeamUpdateAdminResponse response = teamService.updateTeamAdmin(user, team_id, request);

        return ResponseEntity.status(TeamResponseCode.NO_CONTENT_CHANGE_TEAM_ADMIN.getHttpStatus())
                .body(new CommonResponseDto(TeamResponseCode.NO_CONTENT_CHANGE_TEAM_ADMIN.getHttpStatus().value(),
                        TeamResponseCode.NO_CONTENT_CHANGE_TEAM_ADMIN.getMessage(), response));

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "TEAM-004",summary = "팀 멤버 추방")
    @DeleteMapping("/{team_id}/user")
    public ResponseEntity<CommonResponseDto> deleteTeamUser(@AuthUser User user, @PathVariable Long team_id,
            @RequestBody TeamDeleteUserRequest request
    ) {

        TeamDeleteUserResponse response = teamService.deleteTeamUser(user, team_id, request);

        return ResponseEntity.status(TeamResponseCode.NO_CONTENT_EXPEL_TEAM_MEMBER.getHttpStatus())
                .body(new CommonResponseDto(TeamResponseCode.NO_CONTENT_EXPEL_TEAM_MEMBER.getHttpStatus().value(),
                        TeamResponseCode.NO_CONTENT_EXPEL_TEAM_MEMBER.getMessage(), response));

    }



}
