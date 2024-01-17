package com.sparta.devquiz.domain.mypage.service;

import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.service.TeamUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserTeamUserService {

  private final TeamUserService teamUserService;

  public void deleteTeamUser(Long teamId, Long userId) {
    teamUserService.deleteTeamUser(teamId, userId);
  }

  public List<TeamUser> getTeamUserByUser(Long userId) {
    return teamUserService.getTeamUserByUser(userId);
  }

  public List<TeamUser> getTeamUserByUserAndWait(Long userId) {
    return teamUserService.getTeamUserByUserAndWait(userId);
  }

  public void acceptInvitation(Long teamId, Long userId) {
    teamUserService.acceptInvitation(teamId, userId);
  }

  public void rejectInvitation(Long teamId, Long userId) {
    teamUserService.rejectInvitation(teamId, userId);
  }
}