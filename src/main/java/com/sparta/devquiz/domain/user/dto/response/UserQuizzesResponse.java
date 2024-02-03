package com.sparta.devquiz.domain.user.dto.response;

import com.sparta.devquiz.domain.quiz.dto.response.QuizGetByUserResponse;
import com.sparta.devquiz.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "유저 퀴즈 정보 응답 dto")
public class UserQuizzesResponse {

  @Schema(description = "유저 id", defaultValue = "1")
  private Long userId;

  @Schema(description = "유저 이름", defaultValue = "봉골레파스타")
  private String username;

  @Schema(description = "퀴즈 정보", defaultValue = "퀴즈 1")
  private List<QuizGetByUserResponse> quizList;

  public static UserQuizzesResponse of(User user, List<QuizGetByUserResponse> quizList) {
    return UserQuizzesResponse
        .builder()
        .userId(user.getId())
        .username(user.getUsername())
        .quizList(quizList)
        .build();
  }
}