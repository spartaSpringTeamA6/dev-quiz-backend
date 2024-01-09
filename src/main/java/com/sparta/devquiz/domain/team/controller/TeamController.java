package com.sparta.devquiz.domain.team.controller;

import com.sparta.devquiz.domain.team.dto.request.TeamCreateRequest;
import com.sparta.devquiz.domain.team.dto.response.TeamCreateResponse;
import com.sparta.devquiz.domain.team.response.TeamResponseCode;
import com.sparta.devquiz.domain.team.service.TeamService;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "6. Team API", description = "Team 관련 API 입니다.")
public class TeamController {

    private final TeamService teamService;

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "팀 생성")
    @PostMapping("/team")
    public ResponseEntity<CommonResponseDto> createTeam(
            @RequestBody TeamCreateRequest request
    ) {

        TeamCreateResponse response = teamService.createTeam(request);

        return ResponseEntity.status(TeamResponseCode.CREATED_TEAM.getHttpStatus())
                .body(new CommonResponseDto(TeamResponseCode.CREATED_TEAM.getHttpStatus(),
                        TeamResponseCode.CREATED_TEAM.getMessage(), response));

    }

}
