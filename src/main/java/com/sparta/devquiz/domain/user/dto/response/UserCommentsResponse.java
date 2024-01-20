package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.comment.dto.response.CommentInfoResponse;
import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "유저 작성 댓글 정보 응답 dto")
public class UserCommentsResponse {
  @Schema(description = "유저 id", defaultValue = "1")
  private Long userId;

  @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
  private String username;

  @Schema(description = "유저 댓글", defaultValue = "댓글 1")
  private List<CommentInfoResponse> CommentInfoList;

  public static UserCommentsResponse of(User user, List<CommentInfoResponse> CommentInfoList) {
    return UserCommentsResponse
        .builder()
        .userId(user.getId())
        .username(user.getUsername())
        .CommentInfoList(CommentInfoList)
        .build();
  }
}