package com.sparta.devquiz.domain.mypage.service;

import com.sparta.devquiz.domain.team.entity.Team;
import com.sparta.devquiz.domain.team.entity.TeamUser;
import com.sparta.devquiz.domain.team.service.TeamService;
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
//  private final TeamService teamService;

  public void deleteTeamUser(Team team, User user) {
    teamUserService.deleteTeamUser(team, user);
  }

  public List<TeamUser> getTeamUserByUser(User user) {
    return teamUserService.getTeamUserByUser(user);
  }

  public List<TeamUser> getTeamUserByUserAndWait(User user) {
    return teamUserService.getTeamUserByUserAndWait(user);
  }

  public void acceptInvitation(User user, Long teamId) {
//    teamService.acceptInvitationTeamUser(user,teamId);
  }

  public void rejectInvitation(User user, Long teamId) {
//    Team team = teamService.getTeamById(teamId);
//    teamUserService.rejectInvitation(team, user);
  }
}