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
public class UserScoreResponse {

    @Column
    @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
    private String username;
  
    @Column
    @Schema(description = "유저 스코어", defaultValue = "100")
    private int weekScore;

  public static UserScoreResponse of(User user) {
    return UserScoreResponse.builder()
        .username(user.getUsername())
        .weekScore(user.getWeekScore())
        .build();
  }

  public static List<UserScoreResponse> of(List<User> userList) {
    return userList.stream()
        .map(i -> UserScoreResponse.of(i))
        .collect(Collectors.toList());
  }
}
