package com.sparta.devquiz.global.scheduler;

import com.sparta.devquiz.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Scheduler {

  private final UserRepository userRepository;

  @Scheduled(cron = "0 0 0 * * 1")
  @Transactional
  public void resetWeekScore() {
    userRepository.updateWeekScore();
  }
}