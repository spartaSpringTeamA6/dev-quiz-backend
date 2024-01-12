package com.sparta.devquiz.domain.team.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "팀 내의 나의 랭킹 조회 응답 dto")
public class TeamGetMyRankingResponse {

    @Column
    private Long ranking;
}
