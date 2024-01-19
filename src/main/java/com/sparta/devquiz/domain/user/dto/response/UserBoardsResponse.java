package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.board.dto.response.BoardDetailsResponse;
import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "유저 작성 보드 정보 응답 dto")
public class UserBoardsResponse {
  @Schema(description = "유저 id", defaultValue = "1")
  private Long userId;

  @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
  private String username;

  @Schema(description = "유저 보드", defaultValue = "질문 1")
  private List<BoardDetailsResponse> boardInfoList;

  public static UserBoardsResponse of(User user, List<BoardDetailsResponse> boardInfoList) {
    return UserBoardsResponse
        .builder()
        .userId(user.getId())
        .username(user.getUsername())
        .boardInfoList(boardInfoList)
        .build();
  }
}