package com.sparta.devquiz.domain.mypage.service;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.service.TeamUserService;
import com.sparta.devquiz.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserTeamUserService {

  private final TeamUserService teamUserService;

  public void deleteTeamUser(Team team, User user) {
    teamUserService.deleteTeamUser(team, user);
  }

  public List<TeamUser> getTeamUserByUser(User user) {
    return teamUserService.getTeamUserByUser(user);
  }

  public List<TeamUser> getTeamUserByUserAndWait(User user) {
    return teamUserService.getTeamUserByUserAndWait(user);
  }

  public void acceptInvitation(Long teamId, Long userId) {
    teamUserService.acceptInvitation(teamId, userId);
  }

  public void rejectInvitation(Long teamId, Long userId) {
    teamUserService.rejectInvitation(teamId, userId);
  }
}