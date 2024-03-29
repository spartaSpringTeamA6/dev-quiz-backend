package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "유저 스코어 응답 dto")
public class UserRankingResponse {

    @Column
    @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
    private String username;

    @Column
    @Schema(description = "유저 랭킹", defaultValue = "45")
    private int weekRanking;

    public static UserRankingResponse of(User user, int ranking) {
        return UserRankingResponse.builder()
                .username(user.getUsername())
                .weekRanking(ranking)
                .build();
    }

    public static List<UserScoreResponse> of(List<User> userList) {
        return userList.stream()
                .map(i -> new UserScoreResponse(i.getUsername(),i.getWeekScore()))
                .collect(Collectors.toList());
    }
}
