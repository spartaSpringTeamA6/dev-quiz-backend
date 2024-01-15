package com.sparta.devquiz.domain.team.dto.response;

import com.sparta.devquiz.domain.user.dto.response.UserScoreResponse;
import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Schema(description = "팀 내 유저 랭킹 및 나의 랭킹 조회 응답 dto")
public class TeamGetUserRankingResponse {

//    @Column
//    private UserScoreResponse myRanking;
//
//    @Column
//    private List<UserScoreResponse>
//
//
//    public class UserRanking{
//        @Column
//        private String username;
//
//        @Column
//        private int ranking;
//
//    }
}

