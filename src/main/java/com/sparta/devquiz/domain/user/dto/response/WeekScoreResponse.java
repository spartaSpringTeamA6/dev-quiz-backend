package com.sparta.devquiz.domain.user.dto.response;

public class WeekScoreResponse {
  private final int weekScore;

  public WeekScoreResponse(int weekScore) {
    this.weekScore = weekScore;
  }

  public int getWeekScore() {
    return weekScore;
  }
}
